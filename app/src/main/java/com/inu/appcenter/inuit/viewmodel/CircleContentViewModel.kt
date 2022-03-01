package com.inu.appcenter.inuit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.inu.appcenter.inuit.retrofit.CirclesContentServiceCreator
import com.inu.appcenter.inuit.retrofit.dto.CircleContent

class CircleContentViewModel : ViewModel() {

    val client : CirclesContentServiceCreator

    lateinit var circleContent : LiveData<CircleContent>

    init {
        client = CirclesContentServiceCreator()
    }

    fun requestCircleContent(id:Int){
        circleContent = client.getCircleContent(id)
    }
}