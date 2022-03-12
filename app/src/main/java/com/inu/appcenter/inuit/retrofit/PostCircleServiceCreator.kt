package com.inu.appcenter.inuit.retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.inu.appcenter.inuit.retrofit.dto.CirclePostBody
import com.inu.appcenter.inuit.retrofit.dto.MemberResponse
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class PostCircleServiceCreator {

    private val BASE_URL = "https://inuit.inuappcenter.kr/"
    private val client : PostCircleService


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
        client = retrofit.create(PostCircleService::class.java)
    }

    fun postCircle(token:String, circleBody:CirclePostBody) : LiveData<Int>{
        val liveData = MutableLiveData<Int>()
        val call = client.postCircle(token,circleBody)

        call.enqueue(object: Callback<MemberResponse> {
            override fun onFailure(call: Call<MemberResponse>, t: Throwable) {
                Log.e("error", "${t.message}")
            }

            override fun onResponse(
                call: Call<MemberResponse>,
                response: Response<MemberResponse>
            ) {
                if(response.isSuccessful){
                    Log.d("postCircle 응답 성공!", "onResponse is Successful!")

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

    fun postPhotos(token:String,id:Int, files:List<MultipartBody.Part>) : LiveData<List<Int>>{
        val liveData = MutableLiveData<List<Int>>()
        val call = client.postPhotos(token,id,files)

        call.enqueue(object: Callback<List<Int>> {
            override fun onFailure(call: Call<List<Int>>, t: Throwable) {
                Log.e("error", "${t.message}")
            }

            override fun onResponse(
                call: Call<List<Int>>,
                response: Response<List<Int>>
            ) {
                if(response.isSuccessful){
                    Log.d("postPhotos 응답 성공!", "onResponse is Successful!")

                    val body = response.body()
                    liveData.value = body!!
                }
                else {
                    Log.e("응답 실패", "response is not Successful")
                    liveData.value = listOf(-1)
                    Log.e("errorBody() is ", response.errorBody()!!.string())
                }
            }
        })
        return liveData
    }

    fun setProfileImage(token:String, circleId : Int, photoId : Int) : LiveData<Int>{
        val liveData = MutableLiveData<Int>()
        val call = client.setProfileImage(token, circleId, photoId)

        call.enqueue(object: Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.e("error", "${t.message}")
            }

            override fun onResponse(
                call: Call<Int>,
                response: Response<Int>
            ) {
                if(response.isSuccessful){
                    Log.d("setProfileImage 응답 성공!", "onResponse is Successful!")

                    val body = response.body()
                    liveData.value = body!!
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