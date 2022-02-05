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
import com.inu.appcenter.inuit.retrofit.ServiceCreator

class AllClubListFragment : Fragment() {

    private lateinit var recycler_all_club_list : RecyclerView
    private lateinit var adapter: MultiTypeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_all_club_list, container, false)

        recycler_all_club_list = view.findViewById(R.id.recycler_all_club_list)
        recycler_all_club_list.layoutManager = LinearLayoutManager(context)

        adapter = MultiTypeAdapter()
        recycler_all_club_list.adapter = adapter

        adapter.updateAllClubList()

        return view
    }

}