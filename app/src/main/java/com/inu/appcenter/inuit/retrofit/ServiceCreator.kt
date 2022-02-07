package com.inu.appcenter.inuit.retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceCreator {

    private val BASE_URL = "https://da86-125-180-55-163.ngrok.io/"
    private val client : Service

    init {
        val retrofit:Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            client = retrofit.create(Service::class.java)
    }

    fun getAllClubList(): LiveData<List<Circle>>{

        val liveList: MutableLiveData<List<Circle>> = MutableLiveData()

        val call = client.getAllCircles()
        call.enqueue(object: Callback<Circles> {
            override fun onFailure(call: Call<Circles>, t: Throwable) {
                Log.e("error", "${t.message}")
            }

            override fun onResponse(
                call: Call<Circles>,
                response: Response<Circles>
            ) {
                if(response.isSuccessful){
                    Log.d("응답 성공!", "onResponse is Successful!")
                    val body = response.body()
                    liveList.value = body?.data
                }
                else {
                    Log.e("응답 실패", "response is not Successful")
                }
            }
        })
        return liveList
    }

    fun getDivisionAllClubList(division:String): LiveData<List<Circle>>{

        val liveList: MutableLiveData<List<Circle>> = MutableLiveData()

        val call = client.getDivisionAllCircles(division)
        call.enqueue(object: Callback<Circles> {
            override fun onFailure(call: Call<Circles>, t: Throwable) {
                Log.e("error", "${t.message}")
            }

            override fun onResponse(
                call: Call<Circles>,
                response: Response<Circles>
            ) {
                if(response.isSuccessful){
                    Log.d("응답 성공!", "onResponse is Successful!")
                    val body = response.body()
                    liveList.value = body?.data
                }
                else {
                    Log.e("응답 실패", "response is not Successful")
                }
            }
        })
        return liveList
    }
}