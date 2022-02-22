package com.inu.appcenter.inuit.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.inu.appcenter.inuit.R

class SearchInfoFragment(val message:String) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_search_info, container, false)
        val searchInfo = view.findViewById<TextView>(R.id.tv_search_info)
        searchInfo.text = message
        return view
    }
}