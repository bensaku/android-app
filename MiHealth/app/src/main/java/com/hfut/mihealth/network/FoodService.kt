package com.hfut.mihealth.network

import com.hfut.mihealth.network.DTO.Food
import io.reactivex.Observable
import retrofit2.http.GET

interface FoodService {
    @GET("foods/grouped")
    fun getFood(): Observable<Map<String, List<Food>>>
}