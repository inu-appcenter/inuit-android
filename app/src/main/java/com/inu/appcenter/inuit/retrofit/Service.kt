package com.inu.appcenter.inuit.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface Service {

    @GET("circles")
    fun getAllCircles() : Call<Circles>

}