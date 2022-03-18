package com.inu.appcenter.inuit.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.inu.appcenter.inuit.CircleDetailActivity
import com.inu.appcenter.inuit.R
import com.inu.appcenter.inuit.recycler.MultiTypeAdapter
import com.inu.appcenter.inuit.recycler.OnCircleClick
import com.inu.appcenter.inuit.viewmodel.ClubListViewModel

class AllClubListFragment : Fragment() , OnCircleClick {

    private val viewModel: ClubListViewModel by activityViewModels()
    private lateinit var recycler_all_club_list : RecyclerView
    private lateinit var adapter: MultiTypeAdapter
    private lateinit var animation : LottieAnimationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_all_club_list, container, false)

        recycler_all_club_list = view.findViewById(R.id.recycler_all_club_list)
        recycler_all_club_list.layoutManager = LinearLayoutManager(context)

        adapter = MultiTypeAdapter(this)
        recycler_all_club_list.adapter = adapter

        animation = view.findViewById(R.id.loading_all_club_list)
        animation.playAnimation()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.allClubList.observe(
            viewLifecycleOwner,
            {
                adapter.addListToItems(it)
                animation.visibility = View.GONE
                animation.pauseAnimation()
            })
    }

    override fun onResume() {
        super.onResume()
        viewModel.allClubList.observe(
            viewLifecycleOwner,
            {
                adapter.addListToItems(it)
                animation.visibility = View.GONE
                animation.pauseAnimation()
            })
    }

    override fun startCircleDetail(id: Int,name:String,recruit:Boolean,location:String?,schedule:String?,phone:String?,owner:String?,
                                   division:String, category:String, description:String) {
        val intent = CircleDetailActivity.newIntent(requireActivity(),id,name,recruit,location,schedule,phone,owner,division,category,description)
        startActivity(intent)
    }
}