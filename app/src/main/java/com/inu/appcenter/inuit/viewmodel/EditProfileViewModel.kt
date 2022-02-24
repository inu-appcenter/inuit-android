package com.inu.appcenter.inuit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.inu.appcenter.inuit.retrofit.MemberPatchService
import com.inu.appcenter.inuit.retrofit.MemberPatchServiceCreator

class EditProfileViewModel :ViewModel() {

    val client : MemberPatchServiceCreator
    lateinit var responseId : LiveData<Int>

    init {
        client = MemberPatchServiceCreator()
    }

    fun requestEditMyProfile(token:String,newNickname:String, newPassword:String){
        responseId = client.editMemberInfo(token,newNickname,newPassword)
    }
}