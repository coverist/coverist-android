package dev.yjyoon.coverist.data.remote.model

import com.google.gson.annotations.SerializedName

data class Cover(
    @SerializedName("cover_id") val coverId: Long,
    @SerializedName("book_id") val bookId: Long,
    val title: String,
    val author: String,
    val genre: String,
    @SerializedName("sub_genre") val subGenre: String,
    val tags: List<String>,
    val publisher: String?,
    val url: String
)
