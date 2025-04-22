package com.hfut.mihealth.network.data

import java.util.Date

data class Image(
    val imageId: Int,
    val timestamp: Long,
    val foodName: String,
    val amount: Int,
    val userid: Int,
    val completed: Boolean,
    val date:Date
)