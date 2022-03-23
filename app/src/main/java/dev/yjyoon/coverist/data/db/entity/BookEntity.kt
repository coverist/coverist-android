package dev.yjyoon.coverist.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    val title: String,
    val author: String,
    val genre: String,
    val subGenre: String,
    val tags: List<String>,
    val coverUrls: List<String>,
    val createdDate: String
)
