package com.inu.appcenter.inuit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.inu.appcenter.inuit.retrofit.MemberServiceCreator
import com.inu.appcenter.inuit.retrofit.dto.MemberInfo

class MyProfileViewModel : ViewModel() {

    val memberClient : MemberServiceCreator

    lateinit var memberInfo : LiveData<MemberInfo>

    init {
        memberClient = MemberServiceCreator()
    }

    fun requestMemberInfo(token:String){
        memberInfo = memberClient.requestMemberInfo(token)
    }
}