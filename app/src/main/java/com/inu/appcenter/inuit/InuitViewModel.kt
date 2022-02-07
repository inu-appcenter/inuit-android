package com.inu.appcenter.inuit

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.inu.appcenter.inuit.retrofit.Circle
import com.inu.appcenter.inuit.retrofit.ServiceCreator

class InuitViewModel : ViewModel() {

    val allClubList : LiveData<List<Circle>>

    val mainAllClubList : LiveData<List<Circle>>

    val tempAllClubList : LiveData<List<Circle>>

    val smallAllClubList : LiveData<List<Circle>>

    init {
        allClubList = ServiceCreator().getAllClubList()
        mainAllClubList = ServiceCreator().getDivisionAllClubList("중앙동아리")
        tempAllClubList = ServiceCreator().getDivisionAllClubList("가동아리")
        smallAllClubList = ServiceCreator().getDivisionAllClubList("소모임")
    }
}