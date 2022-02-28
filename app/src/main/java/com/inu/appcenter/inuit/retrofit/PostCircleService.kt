package com.inu.appcenter.inuit.retrofit

import com.inu.appcenter.inuit.retrofit.dto.CirclePostBody
import com.inu.appcenter.inuit.retrofit.dto.MemberResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface PostCircleService {

    @POST("circle")
    fun postCircle(@Header("X-AUTH-TOKEN") token : String, @Body jsonbody:CirclePostBody) : Call<MemberResponse>

    @Multipart
    @POST("user/circle/{id}/photos")
    fun sendPhotos(@Header("X-AUTH-TOKEN") token : String,
                   @Path("id") id :String,
                   @Part files : List<MultipartBody.Part>) : Call<List<Int>>

    @POST("user/circle/{circleId}/photo/{photoId}")
    fun setProfileImage(@Header("X-AUTH-TOKEN") token : String,
                        @Path("circleId") circleId : String,
                        @Path("photoId") photoId : String) : Call<Int>
}