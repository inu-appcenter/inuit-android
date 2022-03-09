package com.inu.appcenter.inuit.retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.inu.appcenter.inuit.retrofit.dto.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FindAccountServiceCreator {

    private val BASE_URL = "https://da86-125-180-55-163.ngrok.io/"
    private val client : FindAccountService

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    init {
        client = retrofit.create(FindAccountService::class.java)
    }

    fun requestVerifyCode(email:String): LiveData<String> {

        val responseEmail : MutableLiveData<String> = MutableLiveData()
        val body = Email(email)
        val call = client.requestVerifyCode(body)

        call.enqueue(object: Callback<EmailResponse> {
            override fun onFailure(call: Call<EmailResponse>, t: Throwable) {
                Log.e("이메일 error", "${t.message}")
            }

            override fun onResponse(
                call: Call<EmailResponse>,
                response: Response<EmailResponse>
            ) {
                if(response.isSuccessful){
                    Log.d("요청 성공!", "onResponse is Successful!")
                    val body = response.body()
                    if(body?.response == email){
                        Log.d("이메일 인증번호 전송 성공!", "onResponse is Successful!")
                        responseEmail.value = body?.response
                    }
                }
                else {
                    Log.e("이메일 인증번호 전송 실패", "response is not Successful")
                    responseEmail.value = "code not sent"
                }
            }
        })
        return responseEmail
    }

    fun requestToken(email: String, code:String) : LiveData<String> {

        val token : MutableLiveData<String> = MutableLiveData()
        val body = VerifyCode(code)
        val call = client.requestToken(body,email)

        call.enqueue(object: Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("인증번호 불일치 error", "${t.message}")
            }

            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                Log.d("요청 성공!", "onResponse is Successful!")
                if(response.isSuccessful){
                    val body = response.body()
                    Log.d("인증번호 일치! 이메일 인증 성공!", "onResponse is Successful!")
                    token.value = body!!
                }
                else {
                    Log.e("인증번호 불일치. 이메일 인증 실패", "response is not Successful")
                    token.value = "code is incorrect"
                }
            }
        })
        return token
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
                }
            }
        })
        return responseId
    }
}