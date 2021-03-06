package com.inu.appcenter.inuit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.inu.appcenter.inuit.retrofit.CirclesContentServiceCreator
import com.inu.appcenter.inuit.retrofit.MemberServiceCreator
import com.inu.appcenter.inuit.retrofit.dto.CircleContent
import com.inu.appcenter.inuit.retrofit.dto.MemberInfo

class MyProfileViewModel : ViewModel() {

    val memberClient : MemberServiceCreator

    lateinit var memberInfo : LiveData<MemberInfo>
    lateinit var deletedCircleId : LiveData<Int>

    val circleClient : CirclesContentServiceCreator
    lateinit var circleContent : LiveData<CircleContent>

    init {
        memberClient = MemberServiceCreator()
        circleClient = CirclesContentServiceCreator()
    }

    fun requestMemberInfo(token:String){
        memberInfo = memberClient.requestMemberInfo(token)
    }

    fun deleteCircle(token:String, id:Int){
        deletedCircleId = memberClient.deleteCircle(token,id)
    }

    fun requestCircleContent(id:Int){
        circleContent = circleClient.getCircleContent(id)
    }
}