package com.hfut.mihealth.network

import com.hfut.mihealth.network.DTO.GuestResponse
import com.hfut.mihealth.network.DTO.LoginRequest
import com.hfut.mihealth.network.DTO.LoginResponse
import com.hfut.mihealth.network.DTO.RegisterRequest
import com.hfut.mihealth.network.DTO.RegisterResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @POST("users/guest")
    fun guestLogin(): Observable<GuestResponse>

    @POST("users/register")
    fun register(@Body registerRequest: RegisterRequest): Observable<RegisterResponse>

    @POST("users/login")
    fun login(@Body loginRequest: LoginRequest): Observable<LoginResponse>

    @GET("/api/public-key")
    fun getPublicKey(): Observable<PublicKeyResponse>
}

data class PublicKeyResponse(
    val publicKey: String
)