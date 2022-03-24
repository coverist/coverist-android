package dev.yjyoon.coverist.repository

import dev.yjyoon.coverist.data.db.dao.BookDao
import dev.yjyoon.coverist.data.db.entity.BookEntity
import dev.yjyoon.coverist.ui.bookshelf.BookshelfState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface BookRepository {
    suspend fun addBook(book: BookEntity)
    suspend fun updateBook(book: BookEntity)
    suspend fun deleteBook(book: BookEntity)
    fun getAllBooks(): Flow<BookshelfState>
}

class BookRepositoryImpl @Inject constructor(
    private val bookDao: BookDao
) : BookRepository {
    override suspend fun addBook(book: BookEntity) {
        bookDao.addBook(book)
    }

    override suspend fun updateBook(book: BookEntity) {
        bookDao.updateBook(book)
    }

    override suspend fun deleteBook(book: BookEntity) {
        bookDao.deleteBook(book)
    }

    override fun getAllBooks(): Flow<BookshelfState> = flow {
        emit(BookshelfState.Loading)
        bookDao.getAllBooks().collect {
            if (it.isEmpty()) emit(BookshelfState.Empty)
            else emit(BookshelfState.Success(it))
        }
    }.catch { e -> emit(BookshelfState.Error(e)) }
}