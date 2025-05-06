package com.hfut.mihealth.util

import android.content.Context

class SharedPreferencesHelper(context: Context) {

    private val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    // 保存Token和UserId
    fun saveTokenAndUserId(token: String, userName: String) {
        prefs.edit().apply {
            putString("user_token", token)
            putString("user_name", userName)
            apply()
        }
    }

    // 获取Token
    fun getToken(): String? = prefs.getString("user_token", null)

    // 获取UserName
    fun getUserName(): String? = prefs.getString("user_name", null)

    // 清除Token和User
    fun clearTokenAndUserId() {
        prefs.edit().apply {
            remove("user_token")
            remove("user_name")
            apply()
        }
    }
}