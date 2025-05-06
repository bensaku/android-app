package com.hfut.mihealth.network.DTO

import java.util.Date

data class Image(
    val imageId: Int,
    val userId: Int,
    val timestamp: Long,
    val date: Date,
    val amount: Int?,
    val calories: Int?,
    val foodName: String?,
    val completed: Boolean,
    )