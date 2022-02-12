package com.inu.appcenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.inu.appcenter.inuit.retrofit.Circle
import com.inu.appcenter.inuit.retrofit.CirclesServiceCreator

class ClubListViewModel() : ViewModel() {

    val client : CirclesServiceCreator

    lateinit var allClubList : LiveData<List<Circle>>
    lateinit var mainClubList : LiveData<List<Circle>>
    lateinit var tempClubList : LiveData<List<Circle>>
    lateinit var smallClubList : LiveData<List<Circle>>

    init {
        client = CirclesServiceCreator()
    }

    fun setDataNoCategory(){
        allClubList = client.getAllClubList()
        mainClubList = client.getDivisionAllClubList("중앙동아리")
        tempClubList = client.getDivisionAllClubList("가동아리")
        smallClubList = client.getDivisionAllClubList("소모임")
    }

    fun setDataWithCategory(category: String){
        allClubList = client.getCategoryAllClubList(category)
        mainClubList = client.getCategoryDivisionClubList(category,"중앙동아리")
        tempClubList = client.getCategoryDivisionClubList(category,"가동아리")
        smallClubList = client.getCategoryDivisionClubList(category,"소모임")
    }
}