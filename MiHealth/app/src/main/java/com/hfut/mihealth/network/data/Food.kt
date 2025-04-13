package com.hfut.mihealth.network.data

data class Food(
    val foodid: Int,
    val name: String,
    val calories: Int,
    val protein: Double,
    val carbs: Double,
    val fat: Double,
    val foodtype: String,
    val othernutritionalinfo: String,
    val imageurl: String
)