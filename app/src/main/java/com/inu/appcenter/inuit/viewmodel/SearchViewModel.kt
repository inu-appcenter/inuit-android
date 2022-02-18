package com.inu.appcenter.inuit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.inu.appcenter.inuit.retrofit.dto.Circles

class SearchViewModel : ViewModel() {

    lateinit var searchResultClubList : LiveData<Circles>

    fun search(word:String){

    }

}