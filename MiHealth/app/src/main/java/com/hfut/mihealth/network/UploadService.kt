package com.hfut.mihealth.network

import androidx.compose.ui.graphics.ImageBitmap
import com.hfut.mihealth.network.client.RetrofitClient
import com.hfut.mihealth.util.ImageUtil
import com.hfut.mihealth.util.ImageUtil.toByteArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UploadService {
    @Multipart
    @POST("/upload")
    suspend fun uploadImage(@Part image: MultipartBody.Part)
}

suspend fun uploadImage(picBitmap: ImageBitmap?) {
    picBitmap?.let { bitmap ->
        withContext(Dispatchers.IO) {
            val byteArray = bitmap.toByteArray() // 使用工具类中的方法
            val bodyPart = ImageUtil.createMultipartBodyPart(byteArray, "photo.jpg") // 使用工具类中的方法


            val service = RetrofitClient.instance.create(UploadService::class.java)
            try {
                service.uploadImage(bodyPart)
            } catch (e: Exception) {
                e.printStackTrace() // 处理异常
            }
        }
    }
}