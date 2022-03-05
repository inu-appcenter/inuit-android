package com.inu.appcenter.inuit.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inu.appcenter.inuit.CircleDetailActivity
import com.inu.appcenter.inuit.viewmodel.ClubListViewModel
import com.inu.appcenter.inuit.R
import com.inu.appcenter.inuit.recycler.MultiTypeAdapter
import com.inu.appcenter.inuit.recycler.OnCircleClick

class SmallClubListFragment : Fragment(), OnCircleClick{

    private val viewModel: ClubListViewModel by activityViewModels()
    private lateinit var recycler_small_club_list : RecyclerView
    private lateinit var adapter: MultiTypeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_small_club_list, container, false)

        recycler_small_club_list = view.findViewById(R.id.recycler_small_club_list)
        recycler_small_club_list.layoutManager = LinearLayoutManager(context)

        adapter = MultiTypeAdapter(this)
        recycler_small_club_list.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.smallClubList.observe(
            viewLifecycleOwner,
            {
                adapter.addListToItems(it)
            })
    }

    override fun startCircleDetail(id: Int,name:String,recruit:Boolean,location:String?,schedule:String?,phone:String?,owner:String?,
                                   division:String, category:String, description:String) {
        val intent = CircleDetailActivity.newIntent(requireActivity(),id,name,recruit,location,schedule,phone,owner,division,category,description)
        startActivity(intent)
    }
}