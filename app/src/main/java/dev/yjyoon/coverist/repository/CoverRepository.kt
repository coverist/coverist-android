package dev.yjyoon.coverist.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.yjyoon.coverist.data.remote.api.CoverService
import dev.yjyoon.coverist.data.remote.model.Book
import dev.yjyoon.coverist.data.remote.model.Cover
import dev.yjyoon.coverist.data.remote.model.Genre
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

interface CoverRepository {
    suspend fun generateCover(book: Book) : List<Cover>
}

class CoverRepositoryImpl @Inject constructor(
    private val coverService: CoverService
) : CoverRepository {
    override suspend fun generateCover(book: Book): List<Cover> {
        var covers = emptyList<Cover>()

        coverService.generateCover(book).enqueue(object : Callback<List<Cover>> {
            override fun onResponse(call: Call<List<Cover>>, response: Response<List<Cover>>) {
                if(response.isSuccessful) covers = response.body()!!
            }

            override fun onFailure(call: Call<List<Cover>>, t: Throwable) {

            }
        })

        return covers
    }
}
