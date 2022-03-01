package com.inu.appcenter.inuit.retrofit

import com.inu.appcenter.inuit.retrofit.dto.CircleGetBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CircleContentService {

    @GET("circles/{id}")
    fun getCircleContent(@Path("id") id : Int) : Call<CircleGetBody>


}