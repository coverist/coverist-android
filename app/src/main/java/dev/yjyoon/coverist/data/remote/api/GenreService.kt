package dev.yjyoon.coverist.data.remote.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GenreService {
    @GET("book/genre")
    fun getGenres(): Call<List<String>>

    @GET("book/genre/{genre}/subgenre")
    fun getSubGenres(@Path(value = "genre") genre: String): Call<List<String>>
}