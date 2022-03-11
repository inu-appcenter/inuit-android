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

class PosterSlideImageViewer : AppCompatActivity() {

    lateinit var viewPager : ViewPager2
    lateinit var curPage : TextView
    lateinit var allPage : TextView
    lateinit var closeButton : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poster_slide_image_viewer)

        window.navigationBarColor = Color.BLACK

        val imagesId: List<Int>? = intent.getIntegerArrayListExtra("imagesId")
        val clickedIndex = intent.getIntExtra("POSTER_INDEX",0)

        closeButton = findViewById(R.id.btn_close_viewer_poster)
        curPage = findViewById(R.id.tv_cur_page_poster)
        allPage = findViewById(R.id.tv_all_page_poster)
        viewPager = findViewById(R.id.slide_view_pager_poster)

        val adapter = PosterSlideImageViewerAdapter(this,imagesId!!)
        viewPager.adapter = adapter
        viewPager.setCurrentItem(clickedIndex,false)

        curPage.text = (viewPager.currentItem + 1).toString()
        allPage.text = imagesId.size.toString()
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
        fun start(context: Context, imagesId: List<Int>, index:Int = 0) {
            val intent = Intent(context, PosterSlideImageViewer::class.java)
            //intent.putParcelableArrayListExtra("images", images as ArrayList<out Parcelable?>?)
            intent.putIntegerArrayListExtra("imagesId",imagesId as java.util.ArrayList<Int>)
            intent.putExtra("POSTER_INDEX",index)
            context.startActivity(intent)
        }
    }
}