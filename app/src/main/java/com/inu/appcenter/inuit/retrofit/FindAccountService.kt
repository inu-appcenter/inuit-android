package com.inu.appcenter.inuit.retrofit

import com.inu.appcenter.inuit.retrofit.dto.*
import retrofit2.Call
import retrofit2.http.*

interface FindAccountService {

    @POST("email/password")
    fun requestVerifyCode(@Body jsonbody: Email) : Call<EmailResponse>

    @POST("verifyCode/password/{email}")
    fun requestToken(@Body jsonbody: VerifyCode, @Path("email") email: String) : Call<String>
}