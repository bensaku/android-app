package com.hfut.mihealth.common.mainPage.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hfut.mihealth.network.UserService
import com.hfut.mihealth.network.client.AuthInterceptor
import com.hfut.mihealth.network.client.RetrofitClient
import com.hfut.mihealth.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class mainViewModelclass(application: Application) : AndroidViewModel(application) {
    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> get() = _token


    fun guestLogin() {
        val userService = RetrofitClient.instance.create(UserService::class.java)
        val sharedPreferences = SharedPreferencesHelper(getApplication())

        viewModelScope.launch {
            userService.guestLogin()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    _token.value = response.token
                    sharedPreferences.saveTokenAndUserId(response.token, response.userid)
                    AuthInterceptor.setToken(response.token)
                }, { error ->
                    error.printStackTrace()
                    // Handle error appropriately
                })
        }
    }
}

