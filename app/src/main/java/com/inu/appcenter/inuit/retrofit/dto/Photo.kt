package com.inu.appcenter.inuit.retrofit.dto

import com.google.gson.annotations.SerializedName

data class Photo(@SerializedName("id") val id : Int, @SerializedName("photoType") val photoType : String?)
