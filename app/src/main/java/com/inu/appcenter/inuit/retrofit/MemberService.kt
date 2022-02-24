package com.inu.appcenter.inuit.retrofit

import com.inu.appcenter.inuit.retrofit.dto.Member
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface MemberService {
    @GET("member")
    fun requestMemberInfo(@Header("X-AUTH-TOKEN") token : String) : Call<Member>
}