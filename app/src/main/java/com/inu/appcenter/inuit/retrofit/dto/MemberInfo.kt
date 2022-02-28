package com.inu.appcenter.inuit.retrofit.dto

import com.google.gson.annotations.SerializedName

data class MemberInfo(
    @SerializedName("id") val id :Int,
    @SerializedName("nickName") val nickname: String,
    @SerializedName("email") val email: String,
    @SerializedName("circleId") var circleId : Int?,
    @SerializedName("circleName") val circleName : String?
)
