package com.inu.appcenter.inuit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.inu.appcenter.inuit.retrofit.LoginServiceCreator
import com.inu.appcenter.inuit.retrofit.MemberServiceCreator
import com.inu.appcenter.inuit.retrofit.dto.MemberInfo

class LoginViewModel : ViewModel() {

    val client : LoginServiceCreator
    lateinit var token : LiveData<String>

    init {
        client = LoginServiceCreator()
    }

    fun requestLogin(email:String,password:String){
        token = client.requestLogin(email,password)
    }
}