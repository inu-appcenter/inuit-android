package com.inu.appcenter.inuit.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inu.appcenter.inuit.InuitViewModel
import com.inu.appcenter.inuit.R
import com.inu.appcenter.inuit.recycler.MultiTypeAdapter
import com.inu.appcenter.inuit.retrofit.Circle
import com.inu.appcenter.inuit.retrofit.ServiceCreator

class SmallClubListFragment : Fragment() {

    private val viewModel: InuitViewModel by activityViewModels()
    private lateinit var recycler_small_club_list : RecyclerView
    private lateinit var adapter: MultiTypeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_small_club_list, container, false)

        recycler_small_club_list = view.findViewById(R.id.recycler_small_club_list)
        recycler_small_club_list.layoutManager = LinearLayoutManager(context)

        adapter = MultiTypeAdapter()
        recycler_small_club_list.adapter = adapter
        setData()

        return view
    }

    private fun setData(){
        if (viewModel.isSelectedCategoryActivity){
            viewModel.smallCategoryClubList.observe(
                viewLifecycleOwner,
                {
                    adapter.addListToItems(it)
                }
            )
        }else{
            viewModel.smallAllClubList.observe(
                viewLifecycleOwner,
                {
                    adapter.addListToItems(it)
                }
            )
        }
    }
}