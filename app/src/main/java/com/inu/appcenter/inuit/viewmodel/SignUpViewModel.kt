package com.inu.appcenter.inuit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.inu.appcenter.inuit.retrofit.SignUpServiceCreator

class SignUpViewModel : ViewModel(){

    val client : SignUpServiceCreator
    lateinit var correctEmail : LiveData<String>
    lateinit var verifiedCode : LiveData<String>

    init {
        client = SignUpServiceCreator()
    }

    fun postEmail(email:String) {
        correctEmail = client.postEmail(email)
    }

    fun verifiedEmailResponse(email: String, code:String){
        verifiedCode = client.isEmailVerified(email, code)
    }

    fun registerMember(email: String, nickName: String, password: String) = client.registerMember(email, nickName, password)
}