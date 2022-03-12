package com.inu.appcenter.inuit.retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.inu.appcenter.inuit.retrofit.dto.CircleContent
import com.inu.appcenter.inuit.retrofit.dto.CircleGetBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CirclesContentServiceCreator {

    private val BASE_URL = "https://inuit.inuappcenter.kr/"
    private val client : CircleContentService

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        client = retrofit.create(CircleContentService::class.java)
    }

    fun getCircleContent(id:Int) : LiveData<CircleContent> {

        val liveData = MutableLiveData<CircleContent>()
        val call = client.getCircleContent(id)

        call.enqueue(object : Callback<CircleGetBody> {
            override fun onFailure(call: Call<CircleGetBody>, t: Throwable) {
                Log.e("error", "${t.message}")
            }

            override fun onResponse(call: Call<CircleGetBody>, response: Response<CircleGetBody>) {
                if(response.isSuccessful){
                    Log.d("응답 성공!", "onResponse is Successful!")
                    val body = response.body()
                    liveData.value = body?.data
                }
                else {
                    Log.e("응답 실패", "response is not Successful")
                }
            }
        })
        return liveData
    }
}