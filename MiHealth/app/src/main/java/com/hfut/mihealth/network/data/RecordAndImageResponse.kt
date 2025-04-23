package com.hfut.mihealth.network.data

import com.google.gson.annotations.SerializedName

data class RecordAndImageResponse (
    @SerializedName("record")
    var record:Map<String, List<RecordResponse>>?,
    @SerializedName("image")
    var image:List<Image>?
    )