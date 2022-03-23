package dev.yjyoon.coverist.data.db

import androidx.room.TypeConverter

object Converters {
    @TypeConverter
    fun listToString(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun stringToList(string: String): List<String> {
        return string.split(",")
    }
}