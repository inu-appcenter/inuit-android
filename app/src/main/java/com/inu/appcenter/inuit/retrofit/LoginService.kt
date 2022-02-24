package com.inu.appcenter.inuit.retrofit

import com.inu.appcenter.inuit.retrofit.dto.LoginBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("login")
    fun requestLogin(@Body jsonbody:LoginBody) : Call<String>
}