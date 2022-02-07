package com.inu.appcenter.inuit.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inu.appcenter.inuit.R
import com.inu.appcenter.inuit.recycler.MultiTypeAdapter
import com.inu.appcenter.inuit.retrofit.Circle
import com.inu.appcenter.inuit.retrofit.ServiceCreator

class TempClubListFragment : Fragment() {

    private lateinit var recycler_temp_club_list : RecyclerView
    private lateinit var adapter: MultiTypeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_temp_club_list, container, false)

        recycler_temp_club_list = view.findViewById(R.id.recycler_temp_club_list)
        recycler_temp_club_list.layoutManager = LinearLayoutManager(context)

        adapter = MultiTypeAdapter()
        recycler_temp_club_list.adapter = adapter
        updateTempClubList()

        return view
    }

    private fun updateTempClubList(){
        val allClubData : LiveData<List<Circle>> = ServiceCreator().getDivisionAllClubList("가동아리")
        allClubData.observe(
            viewLifecycleOwner,
            Observer {
                adapter.addListToItems(allClubData.value)
            })
    }
}