package com.inu.appcenter.inuit.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {

    @GET("circles")
    fun getAllCircles() : Call<Circles>

    @GET("circles/division/{division}")
    fun getDivisionAllCircles(@Path("division") division : String) : Call<Circles>

    @GET("circles/category/{category}")
    fun getCategoryAllCircles(@Path("category") category : String) : Call<Circles>

    @GET("circles/category/{category}/division/{division}")
    fun getCategoryDivisionCircles(@Path("category") category : String, @Path("division") division: String) : Call<Circles>

}