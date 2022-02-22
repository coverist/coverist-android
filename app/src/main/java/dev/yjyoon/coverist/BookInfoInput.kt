package dev.yjyoon.coverist

import androidx.annotation.StringRes

data class Book(
    var title: String,
    var author: String,
    var genre: String,
    var subGenre: String,
    var tags: Set<String>,
    var publisher: String?
)

sealed class BookInfoInput {
    data class Question(
        val number: Int,
        @StringRes val questionText: Int,
        val inputType: InputType
    )

    enum class InputType { TitleAndAuthor, Genre, Tags, Publisher }

    companion object {
        val bookInfoInputQuestions = listOf(
            Question(1, R.string.book_info_q1, InputType.TitleAndAuthor),
            Question(2, R.string.book_info_q2, InputType.Genre),
            Question(3, R.string.book_info_q3, InputType.Genre),
            Question(4, R.string.book_info_q4, InputType.Tags),
            Question(5, R.string.book_info_q5, InputType.Publisher)
        )
    }
}