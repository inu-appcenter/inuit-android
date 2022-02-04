package com.inu.appcenter.inuit.retrofit

import com.google.gson.annotations.SerializedName

data class Circle (
    @SerializedName("id") val id : Int,
    @SerializedName("name") var name : String,
    @SerializedName("introduce") var introduce : String,
    @SerializedName("recruit") var recruit : Boolean,
    @SerializedName("recruitEndDate") var recruitEndDate : String )