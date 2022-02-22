package com.inu.appcenter.inuit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.inu.appcenter.inuit.retrofit.SearchServiceCreator
import com.inu.appcenter.inuit.retrofit.dto.Circle

class SearchViewModel : ViewModel() {

    lateinit var searchResultClubList : LiveData<List<Circle>>

    val client : SearchServiceCreator

    init {
        client = SearchServiceCreator()
    }

    fun search(keyword:String){
        searchResultClubList = client.search(keyword)
    }
}