package com.inu.appcenter.inuit.imageviewer

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PosterSlideImageViewerAdapter(fa: FragmentActivity, private val imagesId: List<Int>) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = imagesId.size

    override fun createFragment(position: Int): Fragment {
        return PosterSlideFragment.newInstance(imagesId[position])
    }
}