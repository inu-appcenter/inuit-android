package com.inu.appcenter.inuit.imageviewer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.inu.appcenter.inuit.R

class PosterFragment() : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_poster, container, false)
        val imageView = view.findViewById<ImageView>(R.id.iv_poster_imageView)

        if(imageId == -1){
            imageView.setImageResource(R.drawable.loading_image)
        }else {
            val requestOptions = RequestOptions()
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.loading_image)

            Glide.with(requireContext())
                .load("https://inuit.inuappcenter.kr/circles/view/photo/${imageId}")
                .fitCenter()
                .apply(requestOptions)
                .into(imageView)
        }

        Log.d("현재 이미지 id","$imageId")
        return view
    }

    companion object{

        private const val IMAGE_ID = "imageId"

        fun newInstance(imageId: Int): Fragment{
            val args = Bundle()
            args.putInt(IMAGE_ID,imageId)
            val fragment = PosterFragment()
            fragment.arguments = args
            return fragment
        }
    }
}