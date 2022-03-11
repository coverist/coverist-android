package dev.yjyoon.coverist.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.yjyoon.coverist.data.remote.api.CoverService
import dev.yjyoon.coverist.data.remote.model.Book
import dev.yjyoon.coverist.data.remote.model.Cover
import dev.yjyoon.coverist.exception.CoverGenerationFailException
import dev.yjyoon.coverist.util.FormDataUtil
import dev.yjyoon.coverist.util.UriUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

interface CoverRepository {
    suspend fun generateCover(context: Context, book: Book): LiveData<List<Cover>>
}

class CoverRepositoryImpl @Inject constructor(
    private val coverService: CoverService
) : CoverRepository {
    override suspend fun generateCover(context: Context, book: Book): LiveData<List<Cover>> {
        val covers = MutableLiveData<List<Cover>>()

        val bookInfo = book.toPartMap()
        val publisherImage =
            book.publisher?.let {
                FormDataUtil.getImageMultipart(
                    "publisher",
                    UriUtil.toFile(context, it)
                )
            }

        coverService.generateCover(bookInfo, publisherImage)
            .enqueue(object : Callback<List<Cover>> {
                override fun onResponse(call: Call<List<Cover>>, response: Response<List<Cover>>) {
                    if (response.isSuccessful) covers.value = response.body()!!
                    else throw CoverGenerationFailException()
                }

                override fun onFailure(call: Call<List<Cover>>, t: Throwable) {
                    throw CoverGenerationFailException()
                }
            })

        return covers
    }
}
