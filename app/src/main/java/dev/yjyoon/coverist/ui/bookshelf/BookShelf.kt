package dev.yjyoon.coverist.ui.bookshelf

import dev.yjyoon.coverist.data.db.entity.BookEntity

sealed class BookShelf {
    object Loading : BookShelf()
    object Empty : BookShelf()
    data class Success(val data: List<BookEntity>) : BookShelf()
    data class Error(val error: Throwable?) : BookShelf()
}