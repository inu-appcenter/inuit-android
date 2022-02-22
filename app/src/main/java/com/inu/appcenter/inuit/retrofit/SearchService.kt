package com.inu.appcenter.inuit.retrofit

import com.inu.appcenter.inuit.retrofit.dto.Circles
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchService {

    @GET("circles/name/{name}")
    fun searchCircles(@Path("name") name :String) : Call<Circles>
}