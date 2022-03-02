package com.inu.appcenter.inuit.retrofit

import com.inu.appcenter.inuit.retrofit.dto.Member
import com.inu.appcenter.inuit.retrofit.dto.MemberResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface MemberService {
    @GET("member")
    fun requestMemberInfo(@Header("X-AUTH-TOKEN") token : String) : Call<Member>

    @DELETE("circle/{id}")
    fun deleteCircle(@Header("X-AUTH-TOKEN") token : String, @Path("id")id:Int) : Call<MemberResponse>
}