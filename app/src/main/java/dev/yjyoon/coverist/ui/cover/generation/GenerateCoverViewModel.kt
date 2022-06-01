package dev.yjyoon.coverist.ui.cover.generation

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yjyoon.coverist.data.db.entity.BookEntity
import dev.yjyoon.coverist.data.remote.model.Book
import dev.yjyoon.coverist.data.remote.model.Cover
import dev.yjyoon.coverist.data.remote.model.Genre
import dev.yjyoon.coverist.exception.NonexistentTagException
import dev.yjyoon.coverist.exception.TagAlreadyExistsException
import dev.yjyoon.coverist.repository.BookRepository
import dev.yjyoon.coverist.repository.CoverRepository
import dev.yjyoon.coverist.repository.GenreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GenerateCoverViewModel @Inject constructor(
    private val genreRepository: GenreRepository,
    private val coverRepository: CoverRepository,
    private val bookRepository: BookRepository
) : ViewModel() {

    private var _uiState by mutableStateOf<GenerateCoverUiState>(GenerateCoverUiState.Waiting)
    val uiState: GenerateCoverUiState get() = _uiState

    val genres: LiveData<List<Genre>> = liveData {
        emit(genreRepository.getGenres())
    }

    var subGenres: LiveData<List<Genre>>? = null

    var bookTitle by mutableStateOf("")
    var bookAuthor by mutableStateOf("")
    var bookGenre by mutableStateOf<Genre?>(null)
    var bookSubGenre by mutableStateOf<Genre?>(null)
    var bookTags = mutableStateListOf<String>()
    var bookPublisher by mutableStateOf("")

    lateinit var covers: List<Cover>
    
    fun editTitle(title: String) {
        bookTitle = title
    }

    fun editAuthor(author: String) {
        bookAuthor = author
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

    fun editPublisher(publisher: String) {
        bookPublisher = publisher
    }

    fun isValidInput(step: Int): Boolean =
        when (step) {
            0 -> bookTitle.trim() != "" && bookAuthor.trim() != ""
            1 -> bookGenre != null
            2 -> bookSubGenre != null
            3 -> bookTags.isNotEmpty()
            4 -> true
            else -> false
        }

    fun generateCover(context: Context) {
        _uiState = GenerateCoverUiState.Generating

        val book = Book(
            title = bookTitle.trim(),
            author = bookAuthor.trim(),
            genre = bookGenre!!.text,
            subGenre = bookSubGenre!!.text,
            tags = bookTags,
            publisher = bookPublisher
        )

        viewModelScope.launch {
            val response = coverRepository.generateCover(context, book)
            if (response.isSuccessful && response.body() != null) {
                covers = response.body()!!
                bookRepository.addBook(coversToBookEntity())
                withContext(Dispatchers.Main) {
                    _uiState = GenerateCoverUiState.Done
                }
            } else {
                withContext(Dispatchers.Main) {
                    _uiState = GenerateCoverUiState.Fail
                }
            }
        }
    }

    private fun coversToBookEntity(): BookEntity {
        return covers[0].let {
            BookEntity(
                bookId = it.bookId,
                title = it.title,
                author = it.author,
                genre = it.genre,
                subGenre = it.subGenre,
                tags = it.tags,
                coverUrls = covers.map { cover -> cover.url },
                createdDate = it.createdDate
            )
        }
    }
}


