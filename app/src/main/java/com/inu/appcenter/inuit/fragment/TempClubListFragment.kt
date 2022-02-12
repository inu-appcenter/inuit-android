package com.inu.appcenter.inuit.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inu.appcenter.viewmodel.ClubListViewModel
import com.inu.appcenter.inuit.R
import com.inu.appcenter.inuit.recycler.MultiTypeAdapter

class TempClubListFragment : Fragment() {

    private val viewModel: ClubListViewModel by activityViewModels()
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
        setData()

        return view
    }

    private fun setData(){
        if (viewModel.isSelectedCategoryActivity){
            viewModel.tempCategoryClubList.observe(
                viewLifecycleOwner,
                {
                    adapter.addListToItems(it)
                }
            )
        }else{
            viewModel.tempAllClubList.observe(
                viewLifecycleOwner,
                {
                    adapter.addListToItems(it)
                }
            )
        }
    }
}