package com.hfut.mihealth.network.client

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response


// 使用 object 关键字定义单例
object AuthInterceptor : Interceptor {

    @Volatile private var token: String? = null

    // 线程安全的设置 token 方法
    fun setToken(newToken: String) {
        synchronized(this) {
            token = newToken
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val currentToken = token // 读取 token 快照，避免在拦截过程中 token 被修改
        val request = chain.request().newBuilder()
            .apply {
                if (!currentToken.isNullOrEmpty()) {
                    addHeader("Authorization", "Bearer $currentToken") // 假设使用 Bearer Token
                }
            }
            .build()
        return chain.proceed(request)
    }
}