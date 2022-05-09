package dev.yjyoon.coverist.repository

import android.content.Context
import dev.yjyoon.coverist.data.remote.api.CoverService
import dev.yjyoon.coverist.data.remote.model.Book
import dev.yjyoon.coverist.data.remote.model.Cover
import retrofit2.Response
import javax.inject.Inject

interface CoverRepository {
    suspend fun generateCover(context: Context, book: Book): Response<List<Cover>>
    suspend fun addCover(bookId: Long): Response<List<Cover>>
}

class CoverRepositoryImpl @Inject constructor(
    private val coverService: CoverService
) : CoverRepository {
    override suspend fun generateCover(context: Context, book: Book): Response<List<Cover>> {
        val bookInfo = book.toPartMap()

        return coverService.generateCover(bookInfo)
    }

    override suspend fun addCover(bookId: Long) = coverService.addCover(bookId)
}
