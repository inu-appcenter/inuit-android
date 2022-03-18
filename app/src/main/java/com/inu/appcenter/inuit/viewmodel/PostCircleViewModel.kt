package com.inu.appcenter.inuit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.inu.appcenter.inuit.retrofit.CirclesContentServiceCreator
import com.inu.appcenter.inuit.retrofit.MemberServiceCreator
import com.inu.appcenter.inuit.retrofit.PatchCircleServiceCreator
import com.inu.appcenter.inuit.retrofit.PostCircleServiceCreator
import com.inu.appcenter.inuit.retrofit.dto.CircleContent
import com.inu.appcenter.inuit.retrofit.dto.CircleDetailBody
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

    val contentClient : CirclesContentServiceCreator
    lateinit var circleContent : LiveData<CircleContent>

    val patchClient : PatchCircleServiceCreator
    lateinit var patchedCircleId : LiveData<Int>

    init {
        client = PostCircleServiceCreator()
        memberClient = MemberServiceCreator()
        contentClient = CirclesContentServiceCreator()
        patchClient = PatchCircleServiceCreator()
    }

    fun postCircle(token:String, circleBody: CircleDetailBody){
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

    fun getPostedCircleContent(id: Int){
        circleContent = contentClient.getCircleContent(id)
    }

    fun deletePhoto(token:String, circleId: Int, photoId: Int){
        patchClient.deletePhoto(token,circleId,photoId)
    }

    fun patchCircle(token: String, id:Int, body: CirclePostBody){
        patchedCircleId = patchClient.patchCircle(token,id,body)
    }
}