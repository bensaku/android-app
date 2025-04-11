package com.hfut.mihealth.network

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

 object RetrofitClient {
    private val BASE_URL = "http://192.168.1.110:8080/"

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}

fun fetchFoodData() {
    //todo 网络请求
    val disposable = CompositeDisposable()

    val foodService = RetrofitClient.instance.create(FoodService::class.java)

    foodService.getFood()
        .subscribeOn(Schedulers.io()) // 在IO线程执行网络请求
        .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理结果
        .subscribe({ response ->
            for ((key, foodItemList) in response) {
                println("Key: $key")
                for (foodItem in foodItemList) {
                    println("Food ID: ${foodItem.foodid}, Name: ${foodItem.name}")
                }
            }
        }, { error ->
            error.printStackTrace()
        }).let(disposable::add) // 将订阅加入CompositeDisposable以管理生命周期
}