package com.inu.appcenter.inuit.retrofit

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignUpServiceCreator {

    private val BASE_URL = "https://da86-125-180-55-163.ngrok.io/"
    private val client : SignUpService

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        client = retrofit.create(SignUpService::class.java)
    }

    fun isEmailPosted(email:String) : Boolean {

        var isSend = false
        val body = Email(email)
        val call = client.postEmail(body)

        call.enqueue(object: Callback<EmailResponse> {
            override fun onFailure(call: Call<EmailResponse>, t: Throwable) {
                Log.e("error", "${t.message}")
                isSend = false
            }

            override fun onResponse(
                call: Call<EmailResponse>,
                response: Response<EmailResponse>
            ) {
                if(response.isSuccessful){
                    Log.d("응답 성공!", "onResponse is Successful!")
                    isSend = true
                }
                else {
                    Log.e("응답 실패", "response is not Successful")
                    isSend = false
                }
            }
        })
        return isSend
    }

    fun isEmailVerified(email: String, code:String) : Boolean{

        var isVerified = false
        val body = VerifyCode(code)
        val call = client.postVerifyCode(body,email)

        call.enqueue(object: Callback<EmailResponse> {
            override fun onFailure(call: Call<EmailResponse>, t: Throwable) {
                Log.e("error", "${t.message}")
                isVerified = false
            }

            override fun onResponse(
                call: Call<EmailResponse>,
                response: Response<EmailResponse>
            ) {
                if(response.isSuccessful){
                    Log.d("응답 성공!", "onResponse is Successful!")
                    isVerified = true
                }
                else {
                    Log.e("응답 실패", "response is not Successful")
                    isVerified = false
                }
            }
        })
        return isVerified
    }

}