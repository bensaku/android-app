package com.hfut.mihealth.util

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream

object ImageUtil {
    // 将ImageProxy转换为ImageBitmap
    @OptIn(ExperimentalGetImage::class)
    fun ImageProxy.toImageBitmap(): ImageBitmap {
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.image!!.planes[0].buffer))
        } else {
            // For older versions, you might need to implement a fallback or use another method
            TODO("Implement for API levels below P")
        }
        return bitmap.asImageBitmap()
    }

    // 将ImageBitmap转换为字节数组
    fun ImageBitmap.toByteArray(format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG, quality: Int = 100): ByteArray {
        val byteStream = ByteArrayOutputStream()
        this.asAndroidBitmap().compress(format, quality, byteStream)
        return byteStream.toByteArray()
    }

    // 创建用于上传的MultipartBody.Part
    fun createMultipartBodyPart(byteArray: ByteArray, fileName: String): MultipartBody.Part {
        val requestBody: RequestBody = RequestBody.create(MediaType.parse("image/jpeg"), byteArray)
        return MultipartBody.Part.createFormData("image", fileName, requestBody)
    }
}