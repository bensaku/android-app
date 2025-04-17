package com.hfut.mihealth.network

import com.hfut.mihealth.network.data.Record
import com.hfut.mihealth.network.data.RecordResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RecordService {
    @POST("records/addBatch")
    fun addRecord(@Body records: List<Record>): Observable<Boolean>

    //rxjava
    @GET("records/diet")
    fun getDietRecord_Rx(
        @Query("date") date: String
    ): Observable<List<Map<String, List<RecordResponse>>>>

    //协程
    @GET("records/diet")
    suspend fun getDietRecord(
        @Query("date") date: String
    ): Map<String, List<RecordResponse>>
    //Map<String, List<Map<String, Any>>>
    //Map<String, List<RecordResponse>>
}