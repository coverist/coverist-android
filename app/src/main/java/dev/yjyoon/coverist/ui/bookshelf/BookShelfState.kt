package dev.yjyoon.coverist.ui.bookshelf

import dev.yjyoon.coverist.data.db.entity.BookEntity

sealed class BookShelfState {
    object Loading : BookShelfState()
    object Empty : BookShelfState()
    data class Success(val data: List<BookEntity>) : BookShelfState()
    data class Error(val error: Throwable?) : BookShelfState()
}