package com.inu.appcenter.inuit.retrofit.dto

import com.google.gson.annotations.SerializedName

data class CircleDetailBody(@SerializedName("name") val name : String,
                            @SerializedName("circleDivision") val circleDivision : String,
                            @SerializedName("circleCategory") val circleCategory : String,
                            @SerializedName("oneLineIntroduce") val oneLineIntroduce : String,
                            @SerializedName("introduce") val introduce : String,
                            @SerializedName("recruit") val recruit : Boolean,
                            @SerializedName("information") val information : String?,
                            @SerializedName("recruitStartDate") val recruitStartDate:String?,
                            @SerializedName("recruitEndDate") val recruitEndDate : String?,
                            @SerializedName("address") val address : String?,
                            @SerializedName("cafeLink") val cafeLink : String?,
                            @SerializedName("openKakao") val openKakaoLink : String?,
                            @SerializedName("phoneNumber") val phoneNumber : String?,
                            @SerializedName("link") val applyLink : String)
