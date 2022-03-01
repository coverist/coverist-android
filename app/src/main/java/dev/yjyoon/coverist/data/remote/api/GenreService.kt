package dev.yjyoon.coverist.data.remote.api

import dev.yjyoon.coverist.data.remote.model.Genre
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GenreService {
    @GET("book/genre")
    fun getGenres(): Call<List<Genre>>

    @GET("book/genre/{genre_id}/subgenre")
    fun getSubGenres(@Path(value = "genre_id") genreId: Int): Call<List<Genre>>
}