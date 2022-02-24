package com.inu.appcenter.inuit.retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.inu.appcenter.inuit.retrofit.dto.Member
import com.inu.appcenter.inuit.retrofit.dto.MemberPatchBody
import com.inu.appcenter.inuit.retrofit.dto.MemberResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MemberPatchServiceCreator {

    private val BASE_URL = "https://da86-125-180-55-163.ngrok.io/"
    private val client : MemberPatchService

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    init {
        client = retrofit.create(MemberPatchService::class.java)
    }

    fun editMemberInfo(token:String,newNickname:String,newPassword:String) : LiveData<Int> {
        val responseId = MutableLiveData<Int>()
        val body = MemberPatchBody(newNickname,newPassword)
        val call = client.editMemberInfo(token,body)

        call.enqueue(object: Callback<MemberResponse> {
            override fun onFailure(call: Call<MemberResponse>, t: Throwable) {
                Log.e("error", "${t.message}")
            }

            override fun onResponse(
                call: Call<MemberResponse>,
                response: Response<MemberResponse>
            ) {
                if(response.isSuccessful){
                    Log.d("Member PATCH 응답 성공!", "onResponse is Successful!")

                    val body = response.body()
                    responseId.value = body?.id
                    Log.d("수정 id :", "${responseId.value}")
                }
                else {
                    Log.e("응답 실패", "response is not Successful")
                }
            }
        })
        return responseId
    }
}