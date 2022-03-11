package dev.yjyoon.coverist.data.remote.model

import android.net.Uri
import com.google.gson.annotations.SerializedName
import dev.yjyoon.coverist.util.FormDataUtil.getTextRequestBody
import okhttp3.RequestBody

data class Book(
    val title: String,
    val author: String,
    val genre: String,
    @SerializedName("sub_genre") val subGenre: String,
    val tags: List<String>,
    val publisher: Uri?
) {
    fun toPartMap(): MutableMap<String, RequestBody> {
        return hashMapOf(
            "title" to getTextRequestBody(title),
            "author" to getTextRequestBody(author),
            "genre" to getTextRequestBody(genre),
            "sub_genre" to getTextRequestBody(subGenre),
            "tags" to getTextRequestBody(tags.joinToString(",", "", ""))
        )
    }
}