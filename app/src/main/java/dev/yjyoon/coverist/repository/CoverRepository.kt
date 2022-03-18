package dev.yjyoon.coverist.repository

import android.content.Context
import dev.yjyoon.coverist.data.remote.api.CoverService
import dev.yjyoon.coverist.data.remote.model.Book
import dev.yjyoon.coverist.data.remote.model.Cover
import dev.yjyoon.coverist.util.FormDataUtil
import dev.yjyoon.coverist.util.UriUtil
import retrofit2.Response
import javax.inject.Inject

interface CoverRepository {
    suspend fun generateCover(context: Context, book: Book): Response<List<Cover>>
}

class CoverRepositoryImpl @Inject constructor(
    private val coverService: CoverService
) : CoverRepository {
    override suspend fun generateCover(context: Context, book: Book): Response<List<Cover>> {
        val bookInfo = book.toPartMap()
        val publisherImage =
            book.publisher?.let {
                FormDataUtil.getImageMultipart(
                    "publisher",
                    UriUtil.toFile(context, it)
                )
            }

        return coverService.generateCover(bookInfo, publisherImage)
    }
}
