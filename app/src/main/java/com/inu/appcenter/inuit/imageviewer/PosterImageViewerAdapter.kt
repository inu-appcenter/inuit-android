package com.inu.appcenter.inuit.imageviewer

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PosterImageViewerAdapter(fa: FragmentActivity, private val images: List<Int>) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = images.size

    override fun createFragment(position: Int): Fragment {
        return PosterFragment(images[position])
    }
}