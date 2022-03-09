package com.inu.appcenter.inuit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.inu.appcenter.inuit.retrofit.NewPasswordServiceCreator
import com.inu.appcenter.inuit.retrofit.dto.MemberInfo

class NewPasswordViewModel : ViewModel() {

    val client : NewPasswordServiceCreator

    lateinit var memberInfo : LiveData<MemberInfo>
    lateinit var responseId : LiveData<Int>

    init {
        client = NewPasswordServiceCreator()
    }

    fun requestMemberInfo(token : String){
        memberInfo = client.requestMemberInfo(token)
    }

    fun patchNewPassword(token: String,nickname:String,newPassword:String){
        responseId = client.patchNewPassword(token,nickname,newPassword)
    }
}