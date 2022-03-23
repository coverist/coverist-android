package dev.yjyoon.coverist.data.db.dao

import androidx.room.*
import dev.yjyoon.coverist.data.db.entity.BookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Insert
    suspend fun addBook(bookEntity: BookEntity)

    @Update
    suspend fun updateBook(bookEntity: BookEntity)

    @Delete
    suspend fun deleteBook(bookEntity: BookEntity)

    @Query("SELECT * FROM book")
    fun getAllBooks(): Flow<List<BookEntity>>
}