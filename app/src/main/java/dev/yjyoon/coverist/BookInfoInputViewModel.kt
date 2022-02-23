package dev.yjyoon.coverist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class BookInfoInputViewModel : ViewModel() {
    var bookTitle by mutableStateOf("")
    var bookAuthor by mutableStateOf("")
    var bookGenre by mutableStateOf("")
    var bookSubGenre by mutableStateOf("")
    var bookTags = mutableStateListOf<String>()
    var bookPublisher by mutableStateOf("")

    fun editTitle(title: String) {
        bookTitle = title.trim()
    }

    fun editAuthor(author: String) {
        bookAuthor = author.trim()
    }

    fun editGenre(genre: String) {
        bookGenre = genre
    }

    fun editSubGenre(subGenre: String) {
        bookSubGenre = subGenre
    }

    fun addTag(tag: String) {
        if (bookTags.contains(tag)) throw TagAlreadyExistsException()
        bookTags.add(tag)
    }

    fun deleteTag(tag: String) {
        if (!bookTags.contains(tag)) throw NonexistantTagException()
        bookTags.remove(tag)
    }

    fun isValidateInput(step: Int): Boolean =
        when (step) {
            0 -> bookTitle != "" && bookAuthor != ""
            1 -> bookGenre != ""
            2 -> bookSubGenre != ""
            else -> true
        }
}
