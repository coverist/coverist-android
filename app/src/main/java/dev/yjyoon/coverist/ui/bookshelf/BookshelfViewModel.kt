package dev.yjyoon.coverist.ui.bookshelf

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yjyoon.coverist.data.db.entity.BookEntity
import dev.yjyoon.coverist.data.remote.model.Cover
import dev.yjyoon.coverist.repository.BookRepository
import dev.yjyoon.coverist.repository.CoverRepository
import dev.yjyoon.coverist.ui.cover.generation.GenerateCoverUiState
import dev.yjyoon.coverist.util.SaveImageUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BookshelfViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val coverRepository: CoverRepository
) : ViewModel() {

    val bookshelf: StateFlow<BookshelfState> =
        bookRepository.getAllBooks()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = BookshelfState.Loading
            )

    private var _uiState by mutableStateOf<GenerateCoverUiState>(GenerateCoverUiState.Waiting)
    val uiState: GenerateCoverUiState get() = _uiState

    private var _selectedBook by mutableStateOf<BookEntity?>(null)
    val selectedBook: BookEntity? get() = _selectedBook

    private var _selectedCoverIndex by mutableStateOf(0)
    val selectedCoverIndex: Int get() = _selectedCoverIndex

    var newCovers: List<Cover> = emptyList()

    fun selectBook(book: BookEntity) {
        _selectedBook = book
        _selectedCoverIndex = 0
    }

    fun selectCoverIndex(index: Int) {
        _selectedCoverIndex = index
    }

    fun addCover(bookId: Long) {
        _uiState = GenerateCoverUiState.Generating

        viewModelScope.launch {
            val response = coverRepository.addCover(bookId)
            if (response.isSuccessful && response.body() != null) {
                newCovers = response.body()!!

                selectedBook!!.coverUrls += newCovers.map { it.url }
                bookRepository.updateBook(selectedBook!!)

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

    fun confirmGeneration() {
        newCovers = emptyList()
        _uiState = GenerateCoverUiState.Waiting
    }

    fun saveCover(context: Context) {
        val url = selectedBook!!.coverUrls[selectedCoverIndex]
        val filename = selectedBook!!.title + System.currentTimeMillis().toString() + ".jpeg"

        viewModelScope.launch {
            val bitmap = SaveImageUtil.getBitmapFromUrl(url)
            SaveImageUtil.saveImageToStorage(context, bitmap, filename)
        }
    }
}