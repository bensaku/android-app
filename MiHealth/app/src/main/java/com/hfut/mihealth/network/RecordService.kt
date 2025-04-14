package com.hfut.mihealth.network

import com.hfut.mihealth.network.data.Record
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface RecordService {
    @POST("records/addBatch")
    fun addRecord(@Body records: List<Record>): Observable<Boolean>
}