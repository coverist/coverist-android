package dev.yjyoon.coverist.data.remote.api

import dev.yjyoon.coverist.data.remote.model.Genre
import retrofit2.http.GET
import retrofit2.http.Path

interface GenreService {
    @GET("book/genre")
    suspend fun getGenres(): List<Genre>

    @GET("book/genre/{genre_id}/subgenre")
    suspend fun getSubGenres(@Path(value = "genre_id") genreId: Int): List<Genre>
}