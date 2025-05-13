package com.hfut.mihealth.network.client

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    var IP_ADDRESS: String = "192.168.79.111"
        private set
    private const val PORT = "8000"
    private fun buildBaseUrl() = "http://$IP_ADDRESS:$PORT/"
    val BASE_URL = "http://"+ IP_ADDRESS+":8000/"


     val client = OkHttpClient.Builder()
         .addInterceptor(AuthInterceptor)
         .connectTimeout(30, TimeUnit.SECONDS)
         .writeTimeout(30, TimeUnit.SECONDS)
         .readTimeout(30, TimeUnit.SECONDS)
         .build()

    val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ") // 设置期望的日期格式
        .create()


    private var retrofit: Retrofit? = null

    fun getRetrofit(): Retrofit {
        return retrofit ?: createRetrofit()
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(buildBaseUrl())
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .also { retrofit = it }
    }

    fun updateIpAddress(newIp: String) {
        IP_ADDRESS = newIp
        retrofit = createRetrofit() // 重新创建 Retrofit 实例以应用新的 IP
    }
}