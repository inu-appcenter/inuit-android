package com.inu.appcenter.inuit.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {

    @GET("circles")
    fun getAllCircles() : Call<Circles>

    @GET("circles/division/{division}")
    fun getDivisionAllCircles(@Path("division") division : String) : Call<Circles>

}