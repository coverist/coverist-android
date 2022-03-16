package dev.yjyoon.coverist.data.remote.api

import dev.yjyoon.coverist.data.remote.model.Cover
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface CoverService {
    @Multipart
    @POST("book")
    fun generateCover(
        // Kotlin의 경우 @PartMap 사용 시 Map으로는 동작하지 않고 HashMap 또는 MutableMap 으로 해주어야 한다.
        @PartMap bookInfo: MutableMap<String, RequestBody>,
        @Part publisherImage: MultipartBody.Part?
    ): Call<List<Cover>>
}