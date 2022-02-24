package com.inu.appcenter.inuit.retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.inu.appcenter.inuit.retrofit.dto.Member
import com.inu.appcenter.inuit.retrofit.dto.MemberInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MemberServiceCreator {

    private val BASE_URL = "https://da86-125-180-55-163.ngrok.io/"
    private val client : MemberService

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    init {
        client = retrofit.create(MemberService::class.java)
    }

    fun requestMemberInfo(token:String) : LiveData<MemberInfo>{

        val liveData = MutableLiveData<MemberInfo>()
        val call = client.requestMemberInfo(token)

        call.enqueue(object: Callback<Member> {
            override fun onFailure(call: Call<Member>, t: Throwable) {
                Log.e("error", "${t.message}")
            }

            override fun onResponse(
                call: Call<Member>,
                response: Response<Member>
            ) {
                if(response.isSuccessful){
                    Log.d("Member 응답 성공!", "onResponse is Successful!")

                    val body = response.body()
                    liveData.value = body?.data
                    Log.d("내 닉네임은 ", "${liveData.value?.nickname}")
                }
                else {
                    Log.e("응답 실패", "response is not Successful")
                }
            }
        })
        return liveData
    }
}