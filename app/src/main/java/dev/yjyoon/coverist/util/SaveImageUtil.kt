package dev.yjyoon.coverist.util

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.URL

object SaveImageUtil {
    suspend fun getBitmapFromUrl(url: String): Bitmap = withContext(Dispatchers.IO) {
        val connection = URL(url).openConnection()
        connection.doInput = true
        connection.connect()

        val inputStream = connection.getInputStream()
        BitmapFactory.decodeStream(inputStream)
    }

    suspend fun saveImageToStorage(
        context: Context,
        bitmap: Bitmap,
        filename: String,
        mimeType: String = "image/jpeg"
    ) = withContext(Dispatchers.IO) {
        val directory: String = Environment.DIRECTORY_PICTURES
        val mediaContentUri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val outputStream: OutputStream

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, filename)
                put(MediaStore.Images.Media.MIME_TYPE, mimeType)
                put(MediaStore.Images.Media.RELATIVE_PATH, directory)
            }

            context.contentResolver.run {
                val uri = this.insert(mediaContentUri, values) ?: throw IllegalArgumentException()
                outputStream = openOutputStream(uri)!!
            }
        } else {

            val imagePath = Environment.getExternalStoragePublicDirectory(directory).absolutePath
            val image = File(imagePath, filename)
            outputStream = FileOutputStream(image)
        }

        outputStream.use { bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it) }
    }
}