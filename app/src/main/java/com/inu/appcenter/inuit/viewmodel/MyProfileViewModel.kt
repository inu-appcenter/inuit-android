package com.inu.appcenter.inuit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.inu.appcenter.inuit.retrofit.MemberServiceCreator
import com.inu.appcenter.inuit.retrofit.dto.MemberInfo

class MyProfileViewModel : ViewModel() {

    val memberClient : MemberServiceCreator

    lateinit var memberInfo : LiveData<MemberInfo>
    lateinit var deletedCircleId : LiveData<Int>

    init {
        memberClient = MemberServiceCreator()
    }

    fun requestMemberInfo(token:String){
        memberInfo = memberClient.requestMemberInfo(token)
    }

    fun deleteCircle(token:String, id:Int){
        deletedCircleId = memberClient.deleteCircle(token,id)
    }
}