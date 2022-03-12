package com.inu.appcenter.inuit.retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.inu.appcenter.inuit.retrofit.dto.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FindAccountServiceCreator {

    private val BASE_URL = "https://inuit.inuappcenter.kr/"
    private val client : FindAccountService

    val gson = GsonBuilder().setLenient().create()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
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
                responseEmail.value = "code not sent"
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
                    Log.d("인증번호 일치!", "onResponse is Successful!")
                    token.value = body!!
                }
                else {
                    Log.e("인증번호 불일치.", "response is not Successful")
                    token.value = "code is incorrect"
                }
            }
        })
        return token
    }
}