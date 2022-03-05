package com.inu.appcenter.inuit.retrofit.dto

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class CircleContent(
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("nickname") val nickName : String,
    @SerializedName("introduce") val introduce : String,
    @SerializedName("information") val information : String?,
    @SerializedName("circleCategory") val category : String,
    @SerializedName("circleDivision") val division : String,
    @SerializedName("recruit") val recruit : Boolean,
    @SerializedName("recruitStartDate") val recruitStartDate : String?,
    @SerializedName("recruitEndDate") val recruitEndDate : String?,
    @SerializedName("address") val address : String?,
    @SerializedName("cafeLink") val siteLink : String?,
    @SerializedName("link") val applyLink : String?,
    @SerializedName("openKakaoLink") val kakaoLink : String?,
    @SerializedName("phoneNumber") val phoneNumber : String?,
    @SerializedName("photos") val photos : List<Photo>
)
