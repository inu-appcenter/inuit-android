package com.inu.appcenter.inuit.imageviewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.inu.appcenter.inuit.R

class PosterSlideFragment() : Fragment() {

    private lateinit var imageView: ImageView
    var imageId : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null){
            imageId = arguments!!.getInt(IMAGE_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image, container, false)

        imageView = view.findViewById(R.id.iv_image)
        Glide.with(this)
            .load("https://inuit.inuappcenter.kr/circles/view/photo/${imageId}")
            .into(imageView)

        return view
    }

    companion object{

        private const val IMAGE_ID = "imageId"

        fun newInstance(imageId: Int): Fragment{
            val args = Bundle()
            args.putInt(IMAGE_ID,imageId)
            val fragment = PosterSlideFragment()
            fragment.arguments = args
            return fragment
        }
    }
}