package com.inu.appcenter.inuclub.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(fa : FragmentActivity) : FragmentStateAdapter(fa){

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> AllClubListFragment()
            1 -> MainClubListFragment()
            2 -> MajorClubListFragment()
            3 -> SmallClubListFragment()
            else -> AllClubListFragment()
        }
    }
}