package com.inu.appcenter.inuit.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle){

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> AllClubListFragment()
            1 -> MainClubListFragment()
            2 -> TempClubListFragment()
            3 -> SmallClubListFragment()
            else -> AllClubListFragment()
        }
    }
}