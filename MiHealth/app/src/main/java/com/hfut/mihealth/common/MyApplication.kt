package com.hfut.mihealth.common

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.hfut.mihealth.common.mainPage.viewmodel.MainViewModel

class MyApplication : Application(), ViewModelStoreOwner {

    override val viewModelStore = ViewModelStore()



    // 提供一个方法来获取全局的 MainViewModel
    fun getAppViewModel(): MainViewModel {
        return ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        ).get(MainViewModel::class.java)
    }

    override fun onCreate() {
        super.onCreate()
        // 可以在这里进行其他初始化工作
    }
}