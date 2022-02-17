package com.inu.appcenter.inuit.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inu.appcenter.inuit.R
import com.inu.appcenter.inuit.recycler.MultiTypeAdapter

class SearchListFragment : Fragment() {

    private lateinit var recycler_search_list : RecyclerView
    private lateinit var adapter: MultiTypeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_search_list, container, false)

        recycler_search_list = view.findViewById(R.id.recycler_search_list)
        recycler_search_list.layoutManager = LinearLayoutManager(context)

        adapter = MultiTypeAdapter()
        recycler_search_list.adapter = adapter

        return view
    }
}