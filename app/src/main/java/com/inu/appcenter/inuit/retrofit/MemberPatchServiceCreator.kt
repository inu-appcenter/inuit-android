package com.inu.appcenter.inuit.retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.inu.appcenter.inuit.retrofit.dto.ClientErrorBody
import com.inu.appcenter.inuit.retrofit.dto.MemberPatchBody
import com.inu.appcenter.inuit.retrofit.dto.MemberResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MemberPatchServiceCreator {

    private val BASE_URL = "https://inuit.inuappcenter.kr/"
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
                    Log.d("수정된 id :", "${responseId.value}")
                }
                else {
                    Log.e("응답 실패", "response is not Successful")
                    try {
                        val errorBody = response.errorBody() //response.errorBody()는 딱 한 번만 실행되어야 한다.
                        val loginErrorConverter =
                            retrofit.responseBodyConverter<ClientErrorBody>(
                                ClientErrorBody::class.java,
                                ClientErrorBody::class.java.annotations)
                        val convertedBody = loginErrorConverter.convert(errorBody)
                        Log.e("errorBody() message is ", "${convertedBody?.message}" )

                        if(convertedBody?.message == "이미있는 닉네임입니다"){
                            responseId.value = -2
                        }
                    }catch (e:Exception){
                        responseId.value = -1
                        e.printStackTrace()
                    }
                }
            }
        })
        return responseId
    }

    fun deleteMemberInfo(token: String) : LiveData<Int> {
            val responseId = MutableLiveData<Int>()
            val call = client.deleteMemberInfo(token)

            call.enqueue(object: Callback<MemberResponse> {
                override fun onFailure(call: Call<MemberResponse>, t: Throwable) {
                    Log.e("error", "${t.message}")
                }

                override fun onResponse(
                    call: Call<MemberResponse>,
                    response: Response<MemberResponse>
                ) {
                    if(response.isSuccessful){
                        Log.d("Member DELETE 응답 성공!", "onResponse is Successful!")

                        val body = response.body()
                        responseId.value = body?.id
                        Log.d("삭제된 id :", "${responseId.value}")
                    }
                    else {
                        Log.e("응답 실패", "response is not Successful")
                    }
                }
            })
            return responseId
        }

    fun deleteCircle(token:String, id:Int) : LiveData<Int>{
        val livedata = MutableLiveData<Int>()
        val call = client.deleteCircle(token, id)

        call.enqueue(object : Callback<MemberResponse>{

            override fun onFailure(call: Call<MemberResponse>, t: Throwable) {
                Log.e("error", "${t.message}")
            }

            override fun onResponse(
                call: Call<MemberResponse>,
                response: Response<MemberResponse>
            ) {
                if(response.isSuccessful){
                    Log.d("삭제 응답 성공!", "onResponse is Successful!")
                    val body = response.body()
                    livedata.value = body?.id
                }else{
                    Log.e("응답 실패", "response is not Successful")
                }
            }
        })
        return livedata
    }
}