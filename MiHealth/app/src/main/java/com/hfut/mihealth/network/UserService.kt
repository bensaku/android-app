package com.hfut.mihealth.network

import com.hfut.mihealth.network.data.GuestResponse
import io.reactivex.Observable
import retrofit2.http.POST

interface UserService {
    @POST("users/guest")
    fun guestLogin(): Observable<GuestResponse>
}