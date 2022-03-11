package com.inu.appcenter.inuit.imageviewer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
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

        if(imageId == -1){
            imageView.setImageResource(R.drawable.loading_image)
        }else {
            val requestOptions = RequestOptions()
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.loading_image)

            Glide.with(requireContext())
                .load("http://da86-125-180-55-163.ngrok.io:80/circles/view/photo/${imageId}")
                .fitCenter()
                .apply(requestOptions)
                .into(imageView)
        }

        Log.d("현재 이미지 id","$imageId")
        return view
    }
}