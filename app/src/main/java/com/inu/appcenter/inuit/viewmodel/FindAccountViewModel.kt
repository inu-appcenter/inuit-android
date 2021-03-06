package com.inu.appcenter.inuit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.inu.appcenter.inuit.retrofit.FindAccountServiceCreator

class FindAccountViewModel : ViewModel() {

    val client : FindAccountServiceCreator

    lateinit var correctEmail : LiveData<String>
    lateinit var token : LiveData<String>

    init {
        client = FindAccountServiceCreator()
    }

    fun requestVerifyCode(email:String) {
        correctEmail = client.requestVerifyCode(email)
    }

    fun requestToken(email: String, code:String){
        token = client.requestToken(email,code)
    }
}