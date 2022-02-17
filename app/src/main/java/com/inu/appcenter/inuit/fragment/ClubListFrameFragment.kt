package com.inu.appcenter.inuit.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.inu.appcenter.inuit.R

class ClubListFrameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.i("ClubListFrameFragment","프레임 프래그먼트 생성됨")

        val view = inflater.inflate(R.layout.fragment_club_list_frame, container, false)

        val topViewTab = view.findViewById(R.id.tl_mainTopTap) as TabLayout
        val viewPager = view.findViewById(R.id.vp_mainViewPager) as ViewPager2
        val pagerAdapter = PagerAdapter(requireActivity())
        viewPager.adapter = pagerAdapter

        //뷰페이저 물결 애니메이션 제거
        val child = viewPager.getChildAt(0)
        if (child is RecyclerView) {
            child.overScrollMode = View.OVER_SCROLL_NEVER
        }

        val tabTitles = listOf<String>("전체", "중앙 동아리", "가 동아리", "소모임")
        TabLayoutMediator(topViewTab, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        return view
    }
}