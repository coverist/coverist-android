package dev.yjyoon.coverist.data.remote.model

import com.google.gson.annotations.SerializedName

data class Book(
    val title: String,
    val author: String,
    val genre: String,
    @SerializedName("sub_genre") val subGenre: String,
    val tags: List<String>,
    val publisher: String?
)