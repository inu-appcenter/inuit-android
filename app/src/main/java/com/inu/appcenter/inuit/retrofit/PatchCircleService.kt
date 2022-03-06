package com.inu.appcenter.inuit.retrofit

import com.inu.appcenter.inuit.retrofit.dto.CirclePostBody
import com.inu.appcenter.inuit.retrofit.dto.MemberResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface PatchCircleService {

    @DELETE("/user/circle/{circleId}/delete/photos/{photoIds}")
    fun deletePhoto(@Header("X-AUTH-TOKEN") token : String,
                    @Path("circleId") circleId : Int,
                    @Path("photoIds") photoId:Int) : Call<Void>

    @PATCH("circle/{id}")
    fun patchCircle(@Header("X-AUTH-TOKEN") token : String,
                    @Path("id") id : Int,
                    @Body jsonbody: CirclePostBody) : Call<MemberResponse>
}