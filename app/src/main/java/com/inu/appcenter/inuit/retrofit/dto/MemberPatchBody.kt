package com.inu.appcenter.inuit.retrofit.dto

import com.google.gson.annotations.SerializedName

data class MemberPatchBody(@SerializedName("nickName") val nickName:String,
                           @SerializedName("password") val password :String)
