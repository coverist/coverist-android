package dev.yjyoon.coverist.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.yjyoon.coverist.data.remote.api.GenreService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

interface GenreRepository {
    suspend fun getGenres(): LiveData<List<String>>
}

class GenreRepositoryImpl @Inject constructor(
    private val genreService: GenreService
) : GenreRepository {
    override suspend fun getGenres(): LiveData<List<String>> {
        val genres = MutableLiveData<List<String>>()

        genreService.getGenres().enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if(response.isSuccessful) genres.value = response.body()
                else genres.value = emptyList()
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                genres.value = emptyList()
            }
        })

        return genres
    }
}