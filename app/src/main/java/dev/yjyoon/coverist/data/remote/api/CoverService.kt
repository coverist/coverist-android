package dev.yjyoon.coverist.data.remote.api

import dev.yjyoon.coverist.data.remote.model.Book
import dev.yjyoon.coverist.data.remote.model.Cover
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CoverService {
    @POST("book")
    fun generateCover(@Body book: Book): Call<List<Cover>>
}