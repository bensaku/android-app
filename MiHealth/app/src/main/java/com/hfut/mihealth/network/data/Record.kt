package com.hfut.mihealth.network.data

import java.util.Date

data class Record(
    val recordid: Int?,
    val userid: Int?,
    val foodid: Int,
    val meals: String,
    val amount: Int,
    val date: Date
)