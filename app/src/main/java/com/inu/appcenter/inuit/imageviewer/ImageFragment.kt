package com.inu.appcenter.inuit.imageviewer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.model.Image
import com.inu.appcenter.inuit.R

class ImageFragment(private val image : Image) : Fragment() {

    private lateinit var imageView: ImageView

    private var mScaleGestureDetector: ScaleGestureDetector? = null
    private var scaleFactor = 1.0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image, container, false)

        imageView = view.findViewById(R.id.iv_image)
        Glide.with(this)
            .load(image.uri)
            .into(imageView)

        mScaleGestureDetector = ScaleGestureDetector(context, ScaleListener())

        view.setOnTouchListener { view, motionEvent ->
            mScaleGestureDetector!!.onTouchEvent(motionEvent)
        }

        return view
    }

    inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {

            scaleFactor *= scaleGestureDetector.scaleFactor

            // 최소 기본 사이즈, 최대 2배
            scaleFactor = Math.max(1f, Math.min(scaleFactor, 2f))

            // 이미지에 적용
            imageView.scaleX = scaleFactor
            imageView.scaleY = scaleFactor
            return true
        }
    }
}