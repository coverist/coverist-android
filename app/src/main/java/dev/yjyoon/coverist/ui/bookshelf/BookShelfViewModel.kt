package dev.yjyoon.coverist.ui.bookshelf

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yjyoon.coverist.data.db.entity.BookEntity
import dev.yjyoon.coverist.repository.BookRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BookShelfViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {

    val bookshelf: StateFlow<BookShelf> =
        bookRepository.getAllBooks()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = BookShelf.Loading
            )

    private val _selectedBook = MutableLiveData<BookEntity?>()
    val selectedBook: LiveData<BookEntity?> = _selectedBook

    private val _selectedCoverIndex = MutableLiveData(0)
    val selectedCoverIndex: LiveData<Int> = _selectedCoverIndex

    fun selectBook(book: BookEntity) {
        _selectedBook.value = book
        _selectedCoverIndex.value = 0
    }

    fun selectCoverIndex(index: Int) {
        _selectedCoverIndex.value = index
    }
}