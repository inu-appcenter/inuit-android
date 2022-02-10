package com.inu.appcenter.viewmodel

import androidx.lifecycle.ViewModel
import com.inu.appcenter.inuit.retrofit.SignUpServiceCreator

class SignUpViewModel : ViewModel(){

    val client : SignUpServiceCreator

    init {
        client = SignUpServiceCreator()
    }

    fun isCorrectEmail(email:String) = client.isEmailPosted(email)

    fun isVerifiedEmail(email: String, code:String) = client.isEmailVerified(email, code)

}