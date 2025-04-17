package com.hfut.mihealth.network.data

import java.util.Date

data class RecordResponse(
    val userid: Int,
    val foodid: Int,
    val recordid: Int,
    val amount: Int,
    val name: String,
    val calories: Int,
    val protein: Double,
    val carbs: Double,
    val fat: Double,
    val meals: String,
    val imageurl: String,
    val date: Date
)