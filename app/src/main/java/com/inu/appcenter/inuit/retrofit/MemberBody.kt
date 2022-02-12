package com.inu.appcenter.inuit.retrofit

import com.google.gson.annotations.SerializedName

data class MemberBody(@SerializedName("email") val email: String,
                      @SerializedName("nickName") val nickname: String,
                      @SerializedName("password") val password: String)