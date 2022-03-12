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

class SignUpServiceCreator {

    private val BASE_URL = "https://inuit.inuappcenter.kr/"
    private val client : SignUpService

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    init {
        client = retrofit.create(SignUpService::class.java)
    }

    fun postEmail(email:String) : LiveData<String>{

        val responseEmail : MutableLiveData<String> = MutableLiveData()
        val body = Email(email)
        val call = client.postEmail(body)

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
                    try {
                        val errorBody = response.errorBody() //response.errorBody()는 딱 한 번만 실행되어야 한다.
                        val loginErrorConverter = //로그인 오류(이메일, 비밀번호)
                            retrofit.responseBodyConverter<ClientErrorBody>(ClientErrorBody::class.java,ClientErrorBody::class.java.annotations)
                        val convertedBody = loginErrorConverter.convert(errorBody)
                        Log.e("errorBody() message is ", "${convertedBody?.message}" )
//
                        if(convertedBody?.message == "이미 존제하는 이메일입니다."){
                            responseEmail.value = "posted email"
                        }
                    }catch (e:Exception){
                        responseEmail.value = "code not sent"
                        e.printStackTrace()
                    }
                }
            }
        })
        return responseEmail
    }

    fun isEmailVerified(email: String, code:String) : LiveData<String> {

        val responseCode : MutableLiveData<String> = MutableLiveData()
        val body = VerifyCode(code)
        val call = client.postVerifyCode(body,email)

        call.enqueue(object: Callback<EmailResponse> {
            override fun onFailure(call: Call<EmailResponse>, t: Throwable) {
                Log.e("인증번호 불일치 error", "${t.message}")
            }

            override fun onResponse(
                call: Call<EmailResponse>,
                response: Response<EmailResponse>
            ) {
                Log.d("요청 성공!", "onResponse is Successful!")
                if(response.isSuccessful){
                    val body = response.body()
                    if(body?.response == code) {
                        Log.d("인증번호 일치! 이메일 인증 성공!", "onResponse is Successful!")
                        responseCode.value = body?.response
                    }
                }
                else {
                    Log.e("인증번호 불일치. 이메일 인증 실패", "response is not Successful")
                    responseCode.value = "code is incorrect"
                }
            }
        })
        return responseCode
    }

    fun registerMember(email: String, nickName: String, password: String) : LiveData<Int>{

        val liveData = MutableLiveData<Int>()
        val body = MemberRegisterBody(email,nickName,password)
        val call = client.registerMember(body)


        call.enqueue(object: Callback<MemberResponse>{
            override fun onFailure(call: Call<MemberResponse>, t: Throwable) {
                Log.e("회원정보 전송 error", "${t.message}")
            }

            override fun onResponse(
                call: Call<MemberResponse>,
                response: Response<MemberResponse>
            ) {
                if(response.isSuccessful){
                    Log.d("회원정보 전송 성공!", "onResponse is Successful!")
                    val body = response.body()
                    liveData.value  = body?.id
                    Log.d("회원 ID :",liveData.value.toString())
                }
                else {
                    Log.e("회원정보 전송 실패", "response is not Successful")
                    try {
                        val errorBody = response.errorBody() //response.errorBody()는 딱 한 번만 실행되어야 한다.
                        val loginErrorConverter = //로그인 오류(이메일, 비밀번호)
                            retrofit.responseBodyConverter<ClientErrorBody>(ClientErrorBody::class.java,ClientErrorBody::class.java.annotations)
                        val convertedBody = loginErrorConverter.convert(errorBody)
                        Log.e("errorBody() message is ", "${convertedBody?.message}" )
//
                        if(convertedBody?.message == "이미있는 닉네임입니다"){
                            liveData.value = -2
                        }
                    }catch (e:Exception){
                        liveData.value = -1
                        e.printStackTrace()
                    }
                }
            }
        })
        return liveData
    }
}