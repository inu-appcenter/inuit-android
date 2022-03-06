package com.inu.appcenter.inuit.retrofit

import com.inu.appcenter.inuit.retrofit.dto.MemberPatchBody
import com.inu.appcenter.inuit.retrofit.dto.MemberResponse
import retrofit2.Call
import retrofit2.http.*

interface MemberPatchService {

    @PATCH("member")
    fun editMemberInfo(@Header("X-AUTH-TOKEN") token : String, @Body jsonbody: MemberPatchBody) : Call<MemberResponse>

    @DELETE("member")
    fun deleteMemberInfo(@Header("X-AUTH-TOKEN") token : String) : Call<MemberResponse>

    @DELETE("circle/{id}")
    fun deleteCircle(@Header("X-AUTH-TOKEN") token : String, @Path("id")id:Int) : Call<MemberResponse>
}