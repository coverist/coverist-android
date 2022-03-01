package dev.yjyoon.coverist.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.yjyoon.coverist.data.remote.api.GenreService
import dev.yjyoon.coverist.data.remote.model.Genre
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

interface GenreRepository {
    suspend fun getGenres(): LiveData<List<Genre>>
    suspend fun getSubGenres(genreId: Int): LiveData<List<Genre>>
}

class GenreRepositoryImpl @Inject constructor(
    private val genreService: GenreService
) : GenreRepository {
    override suspend fun getGenres(): LiveData<List<Genre>> {
        val genres = MutableLiveData<List<Genre>>()

        genreService.getGenres().enqueue(object : Callback<List<Genre>> {
            override fun onResponse(call: Call<List<Genre>>, response: Response<List<Genre>>) {
                if(response.isSuccessful) genres.value = response.body()
                else genres.value = emptyList()
            }

            override fun onFailure(call: Call<List<Genre>>, t: Throwable) {
                genres.value = emptyList()
            }
        })

        return genres
    }

    override suspend fun getSubGenres(genreId: Int): LiveData<List<Genre>> {
        val subGenres = MutableLiveData<List<Genre>>()

        genreService.getSubGenres(genreId).enqueue(object : Callback<List<Genre>> {
            override fun onResponse(call: Call<List<Genre>>, response: Response<List<Genre>>) {
                if(response.isSuccessful) subGenres.value = response.body()
                else subGenres.value = emptyList()
            }

            override fun onFailure(call: Call<List<Genre>>, t: Throwable) {
                subGenres.value = emptyList()
            }
        })

        return subGenres
    }
}