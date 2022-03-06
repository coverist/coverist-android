package dev.yjyoon.coverist.data.remote.model

data class Cover(
    val coverId: Long,
    val bookId: Long,
    val title: String,
    val author: String,
    val genre: String,
    val subGenre: String,
    val tags: List<String>,
    val publisher: String?,
    val url: String
)
