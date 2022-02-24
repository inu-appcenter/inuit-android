package com.inu.appcenter.inuit.retrofit

import com.inu.appcenter.inuit.retrofit.dto.MemberPatchBody
import com.inu.appcenter.inuit.retrofit.dto.MemberResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH

interface MemberPatchService {

    @PATCH("member")
    fun editMemberInfo(@Header("X-AUTH-TOKEN") token : String, @Body jsonbody: MemberPatchBody) : Call<MemberResponse>
}