package com.inu.appcenter.inuit.retrofit.dto

import com.google.gson.annotations.SerializedName

data class Circle (
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("oneLineIntroduce") val oneLineIntroduce : String,
    @SerializedName("recruit") val recruit : Boolean,
    @SerializedName("recruitEndDate") val recruitEndDate : String,
    @SerializedName("userId") val userId : Int,
    @SerializedName("photoId") val photoId : Int?,
    @SerializedName("introduce") val introduce : String,
    @SerializedName("address") val address : String?,
    @SerializedName("information") val information : String?,
    @SerializedName("phone") val phone : String?,
    @SerializedName("nickname") val nickName : String,
    @SerializedName("circleCategory") val category : String,
    @SerializedName("circleDivision") val division : String)