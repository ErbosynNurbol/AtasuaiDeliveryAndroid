package kz.atasuai.delivery.common

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

object PhotoHelper {
    fun createMultipartFileFromUri(context: Context, imageUri: Uri): MultipartBody.Part? {
        return try {
            val inputStream = context.contentResolver.openInputStream(imageUri) ?: return null
            val mimeType = context.contentResolver.getType(imageUri) ?: "image/png"
            val requestBody = inputStream.readBytes().toRequestBody(mimeType.toMediaTypeOrNull())
            val fileName = getFileNameFromUri(context, imageUri) ?: "avatar.png"

            MultipartBody.Part.createFormData("avatar", fileName, requestBody)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getFileNameFromUri(context: Context, uri: Uri): String? {
        return try {
            val file = File(uri.path ?: return null)
            file.name
        } catch (e: Exception) {
            null
        }
    }
}
