package com.inu.appcenter.inuit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.inu.appcenter.inuit.retrofit.MemberServiceCreator
import com.inu.appcenter.inuit.retrofit.PostCircleServiceCreator
import com.inu.appcenter.inuit.retrofit.dto.CirclePostBody
import com.inu.appcenter.inuit.retrofit.dto.MemberInfo
import okhttp3.MultipartBody

class PostCircleViewModel : ViewModel() {

    val client : PostCircleServiceCreator

    lateinit var postedCircleId : LiveData<Int>
    lateinit var postedImagesId : LiveData<List<Int>>
    lateinit var profileImageId : LiveData<Int>

    val memberClient : MemberServiceCreator
    lateinit var memberInfo : LiveData<MemberInfo>

    init {
        client = PostCircleServiceCreator()
        memberClient = MemberServiceCreator()
    }

    fun postCircle(token:String, circleBody:CirclePostBody){
        postedCircleId = client.postCircle(token, circleBody)
    }

    fun postPhotos(token: String, id:Int, files:List<MultipartBody.Part>){
        postedImagesId = client.postPhotos(token, id, files)
    }

    fun setProfile(token: String, circleId : Int, photoId : Int){
        profileImageId = client.setProfileImage(token, circleId, photoId)
    }

    fun getNewMemberInfo(token: String){
        memberInfo = memberClient.requestMemberInfo(token)
    }
}