package com.inu.appcenter.inuit.retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.inu.appcenter.inuit.retrofit.dto.CirclePostBody
import com.inu.appcenter.inuit.retrofit.dto.MemberResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class PatchCircleServiceCreator {
    private val BASE_URL = "http://inuit.inuappcenter.kr:8081/"
    private val client : PatchCircleService


    val httpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()

    init {
        client = retrofit.create(PatchCircleService::class.java)
    }

    fun deletePhoto(token:String, circleId:Int, photoId:Int) {
        val call = client.deletePhoto(token,circleId,photoId)
        call.enqueue(object :Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("deletePhotop 성공!", "onResponse is Successful!")
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("error", "${t.message}")
            }
        })
    }

    fun patchCircle(token: String, circleId: Int,circleBody:CirclePostBody) : LiveData<Int>{
        val liveData = MutableLiveData<Int>()
        val call = client.patchCircle(token,circleId,circleBody)

        call.enqueue(object: Callback<MemberResponse> {
            override fun onFailure(call: Call<MemberResponse>, t: Throwable) {
                Log.e("error", "${t.message}")
            }

            override fun onResponse(
                call: Call<MemberResponse>,
                response: Response<MemberResponse>
            ) {
                if(response.isSuccessful){
                    Log.d("patchCircle 응답 성공!", "onResponse is Successful!")

                    val body = response.body()
                    liveData.value = body?.id
                }
                else {
                    Log.e("응답 실패", "response is not Successful")
                    Log.e("errorBody() is ", response.errorBody()!!.string())
                    liveData.value = -1
                }
            }
        })
        return liveData
    }
}