package com.hfut.mihealth.network.data

data class RecordAndImageResponse (
    val record:Map<String, List<RecordResponse>>,
    val image:List<Image>
    )