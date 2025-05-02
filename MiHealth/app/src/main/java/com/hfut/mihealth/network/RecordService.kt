package com.hfut.mihealth.network

import com.hfut.mihealth.network.data.Record
import com.hfut.mihealth.network.data.RecordAndImageResponse
import com.hfut.mihealth.network.data.RecordResponse
import com.hfut.mihealth.network.data.WeekReportResponse
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
    ): Observable<RecordAndImageResponse>

    //协程
    @GET("records/diet")
    suspend fun getDietRecord(
        @Query("date") date: String
    ): RecordAndImageResponse

    @GET("records/week")
    fun getWeekRecord_Rx(
        @Query("date") date: String
    ): Observable<Map<String, Map<String, Double>>>

    @GET("records/week")
    suspend fun getWeekRecord(
        @Query("date") date: String
    ): Map<String, Map<String, Double>>

    @GET("records/weekReport")
    fun getWeekReport_Rx(
        @Query("date") date: String
    ): Observable<WeekReportResponse>

    @GET("records/weekReport")
    suspend fun getWeekReport(
        @Query("date") date: String
    ): WeekReportResponse
}