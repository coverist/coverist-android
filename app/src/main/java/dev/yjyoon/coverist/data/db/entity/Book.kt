package dev.yjyoon.coverist.data.db.entity

data class Book(
    val id: Long,
    val title: String,
    val author: String,
    val genre: String,
    val subGenre: String,
    val tags: List<String>,
    val publisherUrl: String?,
    val coverUrls: List<String>
)
