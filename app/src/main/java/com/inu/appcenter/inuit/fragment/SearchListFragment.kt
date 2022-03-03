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
import com.inu.appcenter.inuit.Utility
import com.inu.appcenter.inuit.recycler.MultiTypeAdapter
import com.inu.appcenter.inuit.recycler.OnCircleClick
import com.inu.appcenter.inuit.viewmodel.SearchViewModel

class SearchListFragment : Fragment(), OnCircleClick{

    private val viewModel: SearchViewModel by activityViewModels()
    private lateinit var recycler_search_list : RecyclerView
    private lateinit var adapter: MultiTypeAdapter
    private lateinit var loadingAnimation : LottieAnimationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_search_list, container, false)

        loadingAnimation = view.findViewById(R.id.loading_search_list)
        Utility.pauseLoading(loadingAnimation)

        recycler_search_list = view.findViewById(R.id.recycler_search_list)
        recycler_search_list.layoutManager = LinearLayoutManager(context)

        adapter = MultiTypeAdapter(this)
        recycler_search_list.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Utility.startLoading(loadingAnimation)

        viewModel.searchResultClubList.observe(
            viewLifecycleOwner,
            {
                adapter.addListToItems(it)
                Utility.pauseLoading(loadingAnimation)
            }
        )
    }

    override fun startCircleDetail(id: Int) {
        val intent = CircleDetailActivity.newIntent(requireActivity())
        startActivity(intent)
    }
}