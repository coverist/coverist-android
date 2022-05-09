package dev.yjyoon.coverist.data.remote.api

import dev.yjyoon.coverist.data.remote.model.Cover
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PartMap
import retrofit2.http.Path

interface CoverService {
    @Multipart
    @POST("book")
    suspend fun generateCover(
        // Kotlin의 경우 @PartMap 사용 시 Map으로는 동작하지 않고 HashMap 또는 MutableMap 으로 해주어야 한다.
        @PartMap bookInfo: MutableMap<String, RequestBody>,
    ): Response<List<Cover>>

    @POST("book/{id}")
    suspend fun addCover(@Path("id") id: Long): Response<List<Cover>>
}