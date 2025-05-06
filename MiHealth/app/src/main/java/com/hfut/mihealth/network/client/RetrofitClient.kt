package com.hfut.mihealth.network.client

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    val BASE_URL = "http://192.168.1.102:8000/"


     val client = OkHttpClient.Builder()
         .addInterceptor(AuthInterceptor)
         .connectTimeout(30, TimeUnit.SECONDS)
         .writeTimeout(30, TimeUnit.SECONDS)
         .readTimeout(30, TimeUnit.SECONDS)
         .build()

    val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ") // 设置期望的日期格式
        .create()

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}