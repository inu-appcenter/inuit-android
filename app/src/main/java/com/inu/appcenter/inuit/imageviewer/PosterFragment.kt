package com.inu.appcenter.inuit.imageviewer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.esafirm.imagepicker.model.Image
import com.inu.appcenter.inuit.R

class PosterFragment(private val imageId : Int) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_poster, container, false)
        val imageView = view.findViewById<ImageView>(R.id.iv_poster_imageView)
        Glide.with(requireContext())
            .load("http://da86-125-180-55-163.ngrok.io:80/circles/view/photo/${imageId}")
            .fitCenter()
            .override(400,600)
            .into(imageView)

        return view
    }
}