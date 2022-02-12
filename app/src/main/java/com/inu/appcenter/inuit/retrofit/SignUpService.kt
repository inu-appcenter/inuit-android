package com.inu.appcenter.inuit.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface SignUpService {

    @POST("email")
    fun postEmail( @Body jsonbody: Email) : Call<EmailResponse>
    
    @POST("verifyCode/{email}")
    fun postVerifyCode(@Body jsonbody: VerifyCode, @Path("email") email: String) : Call<EmailResponse>

    @POST("member")
    fun postMember(@Body jsonbody: MemberBody) : Call<MemberResponse>

}