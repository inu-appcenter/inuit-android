package com.inu.appcenter.inuit.imageviewer

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.inu.appcenter.inuit.R
import java.util.*

class SlideImageViewer : AppCompatActivity() {

    lateinit var viewPager : ViewPager2
    lateinit var curPage : TextView
    lateinit var allPage : TextView
    lateinit var closeButton : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slide_image_viewer)

        window.navigationBarColor = Color.BLACK

        val images: List<String>? = intent.getStringArrayListExtra("images")
        val clickedIndex = intent.getIntExtra("INDEX",0)

        closeButton = findViewById(R.id.btn_close_viewer)
        curPage = findViewById(R.id.tv_cur_page)
        allPage = findViewById(R.id.tv_all_page)
        viewPager = findViewById(R.id.slide_view_pager)

        val adapter = SlideImageViewerAdapter(this,images!!)
        viewPager.adapter = adapter
        viewPager.setCurrentItem(clickedIndex,false)

        curPage.text = (viewPager.currentItem + 1).toString()
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
        fun start(context: Context, images: List<String>?, index:Int = 0) {
            val intent = Intent(context, SlideImageViewer::class.java)
            //intent.putParcelableArrayListExtra("images", images as ArrayList<out Parcelable?>?)
            intent.putStringArrayListExtra("images",images as ArrayList<String>)
            intent.putExtra("INDEX",index)
            context.startActivity(intent)
        }
    }
}