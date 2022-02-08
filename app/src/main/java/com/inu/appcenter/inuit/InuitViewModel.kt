package com.inu.appcenter.inuit

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.inu.appcenter.inuit.retrofit.Circle
import com.inu.appcenter.inuit.retrofit.ServiceCreator

class InuitViewModel() : ViewModel() {

    var isSelectedCategoryActivity:Boolean = false

    val client : ServiceCreator

    val allClubList : LiveData<List<Circle>>
    val mainAllClubList : LiveData<List<Circle>>
    val tempAllClubList : LiveData<List<Circle>>
    val smallAllClubList : LiveData<List<Circle>>

    lateinit var category: String
    lateinit var categoryAllClubList : LiveData<List<Circle>>
    lateinit var mainCategoryClubList : LiveData<List<Circle>>
    lateinit var tempCategoryClubList : LiveData<List<Circle>>
    lateinit var smallCategoryClubList : LiveData<List<Circle>>

    init {
        client = ServiceCreator()
        allClubList = client.getAllClubList()
        mainAllClubList = client.getDivisionAllClubList("중앙동아리")
        tempAllClubList = client.getDivisionAllClubList("가동아리")
        smallAllClubList = client.getDivisionAllClubList("소모임")
    }

    fun setDataWithCategory(){
        categoryAllClubList = client.getCategoryAllClubList(category)
        mainCategoryClubList = client.getCategoryDivisionClubList(category,"중앙동아리")
        tempCategoryClubList = client.getCategoryDivisionClubList(category,"가동아리")
        smallCategoryClubList = client.getCategoryDivisionClubList(category,"소모임")
    }
}