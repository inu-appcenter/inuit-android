package com.inu.appcenter.inuit.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceCreator {

    val BASE_URL = "https://da86-125-180-55-163.ngrok.io/"

    fun create() : Service {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Service::class.java)
    }

}