package com.hfut.mihealth.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import java.text.SimpleDateFormat
import java.util.*
import android.widget.Toast
import java.io.File

object healthtUtils {

    /**
     * 检查设备是否连接到网络
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            when {
                activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) -> true
                else -> false
            }
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            networkInfo != null && networkInfo.isConnected
        }
    }

    /**
     * 将Date对象格式化为字符串
     */
    fun formatDate(date: Date, format: String = "yyyy-MM-dd HH:mm:ss"): String {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        return dateFormat.format(date)
    }

    /**
     * 创建一个文件夹如果它不存在的话
     */
    fun createDirIfNotExists(dirPath: String): File {
        val dir = File(dirPath)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return dir
    }

    /**
     * 显示一个短Toast消息
     */
    fun showToastShort(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * 显示一个长Toast消息
     */
    fun showToastLong(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}