package com.inu.appcenter.inuit.imageviewer

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SlideImageViewerAdapter(fa: FragmentActivity, private val images: List<String>) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = images.size

    override fun createFragment(position: Int): Fragment {
        return ImageFragment.newInstance(images[position])
    }
}