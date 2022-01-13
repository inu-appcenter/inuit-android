package com.inu.appcenter.inuclub.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.inu.appcenter.inuclub.R

class ClubListFrameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_club_list_frame, container, false)

        val topViewTab = view.findViewById(R.id.tl_mainTopTap) as TabLayout
        val viewPager = view.findViewById(R.id.vp_mainViewPager) as ViewPager2
        val pagerAdapter = PagerAdapter(requireActivity())
        viewPager.adapter = pagerAdapter

        val tabTitles = listOf<String>("전체", "중앙 동아리", "가 동아리", "소모임")
        TabLayoutMediator(topViewTab, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
        return view
    }

}