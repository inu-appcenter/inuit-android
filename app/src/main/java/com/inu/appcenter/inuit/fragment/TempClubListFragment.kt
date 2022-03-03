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

class TempClubListFragment : Fragment() , OnCircleClick{

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

        adapter = MultiTypeAdapter(this)
        recycler_temp_club_list.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.tempClubList.observe(
            viewLifecycleOwner,
            {
                adapter.addListToItems(it)
            })
    }
    override fun startCircleDetail(id: Int) {
        val intent = CircleDetailActivity.newIntent(requireActivity())
        startActivity(intent)
    }
}