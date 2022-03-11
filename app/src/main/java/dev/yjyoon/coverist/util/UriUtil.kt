package dev.yjyoon.coverist.util

import android.content.Context
import android.net.Uri
import java.io.File

object UriUtil {
    fun toFile(context: Context, uri: Uri): File {
        val fileName = getFileName(uri)

        val file = FileUtil.createTempFile(context, fileName)
        FileUtil.copyToFile(context, uri, file)

        return File(file.absolutePath)
    }

    fun getFileName(uri: Uri): String {
        val temp = uri.path!!.split("/")

        val name = temp[temp.size - 1] + System.currentTimeMillis().toString()
        val ext = temp[temp.size - 2]

        return "$name.$ext"
    }
}