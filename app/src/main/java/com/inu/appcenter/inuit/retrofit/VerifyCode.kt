package com.inu.appcenter.inuit.retrofit

import com.google.gson.annotations.SerializedName

data class VerifyCode(@SerializedName("code") val code : String)