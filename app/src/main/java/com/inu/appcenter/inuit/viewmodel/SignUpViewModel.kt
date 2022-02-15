package com.inu.appcenter.inuit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.inu.appcenter.inuit.retrofit.SignUpServiceCreator

class SignUpViewModel : ViewModel(){

    val client : SignUpServiceCreator
    lateinit var verifiedCode : LiveData<String>

    init {
        client = SignUpServiceCreator()
    }

    fun postEmail(email:String) {
        client.postEmail(email)
    }

    fun verifiedEmailResponse(email: String, code:String){
        verifiedCode = client.isEmailVerified(email, code)
    }

    fun postMember(email: String, nickName: String, password: String) = client.postMember(email, nickName, password)
}