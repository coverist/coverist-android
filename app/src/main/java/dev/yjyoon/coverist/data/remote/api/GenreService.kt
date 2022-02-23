package dev.yjyoon.coverist.data.remote.api

import retrofit2.Call
import retrofit2.http.GET

interface GenreService {
    @GET("book/genre")
    suspend fun getGenres(): Call<List<String>>
}