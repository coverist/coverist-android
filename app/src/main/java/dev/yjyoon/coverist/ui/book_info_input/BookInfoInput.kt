package dev.yjyoon.coverist.ui.book_info_input

import androidx.annotation.StringRes
import dev.yjyoon.coverist.R

sealed class BookInfoInput {
    data class Question(
        val number: Int,
        @StringRes val questionText: Int,
        val inputType: Type
    )

    enum class Type { TitleAndAuthor, Genre, SubGenre, Tags, Publisher }

    companion object {
        val bookInfoInputQuestions = listOf(
            Question(1, R.string.book_info_q1, Type.TitleAndAuthor),
            Question(2, R.string.book_info_q2, Type.Genre),
            Question(3, R.string.book_info_q3, Type.SubGenre),
            Question(4, R.string.book_info_q4, Type.Tags),
            Question(5, R.string.book_info_q5, Type.Publisher)
        )
    }
}