package com.inu.appcenter.inuit.imageviewer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.ImageButton
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.esafirm.imagepicker.model.Image
import com.inu.appcenter.inuit.R

class SlideImageViewer : AppCompatActivity() {

    lateinit var viewPager : ViewPager2
    lateinit var curPage : TextView
    lateinit var allPage : TextView
    lateinit var closeButton : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slide_image_viewer)

        val images: List<Image>? = intent.getParcelableArrayListExtra("images")

        closeButton = findViewById(R.id.btn_close_viewer)
        curPage = findViewById(R.id.tv_cur_page)
        allPage = findViewById(R.id.tv_all_page)
        viewPager = findViewById(R.id.slide_view_pager)

        val adapter = SlideImageViewerAdapter(this,images!!)
        viewPager.adapter = adapter


        allPage.text = images.size.toString()
        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                curPage.text = (position + 1).toString()
            }
        })

        closeButton.setOnClickListener {
            finish()
        }
    }

    companion object {
        fun start(context: Context, images: List<Image?>?) {
            val intent = Intent(context, SlideImageViewer::class.java)
            intent.putParcelableArrayListExtra("images", images as ArrayList<out Parcelable?>?)
            context.startActivity(intent)
        }
    }
}