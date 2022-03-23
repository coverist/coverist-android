package dev.yjyoon.coverist.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.yjyoon.coverist.data.db.dao.BookDao
import dev.yjyoon.coverist.data.db.entity.BookEntity

@Database(
    entities = [BookEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}