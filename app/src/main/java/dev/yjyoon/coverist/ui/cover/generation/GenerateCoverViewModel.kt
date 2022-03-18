package dev.yjyoon.coverist.ui.cover.generation

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yjyoon.coverist.data.remote.model.Book
import dev.yjyoon.coverist.data.remote.model.Cover
import dev.yjyoon.coverist.data.remote.model.Genre
import dev.yjyoon.coverist.exception.NonexistentTagException
import dev.yjyoon.coverist.exception.TagAlreadyExistsException
import dev.yjyoon.coverist.repository.CoverRepository
import dev.yjyoon.coverist.repository.GenreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

sealed class UiState {
    object Input : UiState()
    object Generate : UiState()
    object Show : UiState()
    object Error : UiState()
}

@HiltViewModel
class GenerateCoverViewModel @Inject constructor(
    private val genreRepository: GenreRepository,
    private val coverRepository: CoverRepository
) : ViewModel() {

    private var _uiState by mutableStateOf<UiState>(UiState.Input)
    val uiState: UiState
        get() = _uiState

    lateinit var genres: LiveData<List<Genre>>
    var subGenres: LiveData<List<Genre>>? = null

    init {
        viewModelScope.launch {
            genres = genreRepository.getGenres().asLiveData()
        }
    }

    var bookTitle by mutableStateOf("")
    var bookAuthor by mutableStateOf("")
    var bookGenre by mutableStateOf<Genre?>(null)
    var bookSubGenre by mutableStateOf<Genre?>(null)
    var bookTags = mutableStateListOf<String>()
    var bookPublisher by mutableStateOf<Uri?>(null)
    var isBookPublisherEmpty by mutableStateOf(false)

    lateinit var covers: List<Cover>

    fun editTitle(title: String) {
        bookTitle = title.trim()
    }

    fun editAuthor(author: String) {
        bookAuthor = author.trim()
    }

    fun editGenre(genre: Genre) {
        bookGenre = genre
        bookSubGenre = null
        subGenres = null
    }

    fun editSubGenre(subGenre: Genre) {
        bookSubGenre = subGenre
    }

    fun loadSubGenres(): LiveData<List<Genre>> {
        if (subGenres != null) return subGenres!!

        viewModelScope.launch {
            subGenres = genreRepository.getSubGenres(bookGenre!!.id).asLiveData()
        }

        return subGenres!!
    }

    fun addTag(tag: String) {
        if (bookTags.contains(tag.trim())) throw TagAlreadyExistsException()
        bookTags.add(tag.trim())
    }

    fun deleteTag(tag: String) {
        if (!bookTags.contains(tag.trim())) throw NonexistentTagException()
        bookTags.remove(tag.trim())
    }

    fun isInvalidTag(tag: String): Boolean {
        if (tag.trim().isEmpty()) return true
        if (bookTags.contains(tag.trim())) return true
        return false
    }

    fun isNotFullTags(): Boolean {
        return bookTags.size < 5
    }

    fun editPublisher(uri: Uri?) {
        bookPublisher = uri
    }

    fun deletePublisher() {
        bookPublisher = null
    }

    fun setPublisherEmpty(empty: Boolean) {
        isBookPublisherEmpty = empty
    }

    fun isValidInput(step: Int): Boolean =
        when (step) {
            0 -> bookTitle != "" && bookAuthor != ""
            1 -> bookGenre != null
            2 -> bookSubGenre != null
            3 -> bookTags.isNotEmpty()
            4 -> bookPublisher != null || isBookPublisherEmpty
            else -> false
        }

    fun generateCover(context: Context) {
        _uiState = UiState.Generate

        val book = Book(
            title = bookTitle,
            author = bookAuthor,
            genre = bookGenre!!.text,
            subGenre = bookSubGenre!!.text,
            tags = bookTags,
            publisher = bookPublisher
        )

        viewModelScope.launch {
            val response = coverRepository.generateCover(context, book)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    covers = response.body()!!
                    _uiState = UiState.Show
                }
            }
        }
    }
}
