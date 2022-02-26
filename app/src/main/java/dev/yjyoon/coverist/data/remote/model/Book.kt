package dev.yjyoon.coverist.data.remote.model

data class Book(
    var title: String,
    var author: String,
    var genre: String,
    var subGenre: String,
    var tags: MutableList<String>,
    var publisher: String?
)