package com.hfut.mihealth.util

import android.content.Context

class SharedPreferencesHelper(context: Context) {

    private val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    // 保存Token和UserId
    fun saveTokenAndUserId(token: String, userId: Int) {
        prefs.edit().apply {
            putString("user_token", token)
            putInt("user_id", userId)
            apply()
        }
    }

    // 获取Token
    fun getToken(): String? = prefs.getString("user_token", null)

    // 获取UserId
    fun getUserId(): Int = prefs.getInt("user_id", -1)

    // 清除Token和UserId
    fun clearTokenAndUserId() {
        prefs.edit().apply {
            remove("user_token")
            remove("user_id")
            apply()
        }
    }
}