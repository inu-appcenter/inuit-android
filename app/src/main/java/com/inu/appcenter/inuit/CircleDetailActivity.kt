package com.inu.appcenter.inuit

import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.inu.appcenter.inuit.imageviewer.PosterImageViewerAdapter
import com.inu.appcenter.inuit.imageviewer.SlideImageViewerAdapter
import com.inu.appcenter.inuit.viewmodel.CircleDetailViewModel

class CircleDetailActivity : AppCompatActivity() {

    private val viewModel by viewModels<CircleDetailViewModel>()

    private lateinit var circleNameTitle : TextView
    private lateinit var location : TextView
    private lateinit var schedule : TextView
    private lateinit var phone : TextView
    private lateinit var owner : TextView
    private lateinit var recruitState : TextView
    private lateinit var division : TextView
    private lateinit var category : TextView
    private lateinit var description: TextView

    private val imagesId = arrayListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle_detail)

        circleNameTitle = findViewById(R.id.tv_top_title_circle_name)
        circleNameTitle.text = intent.getStringExtra("CIRCLE_NAME")

        location = findViewById(R.id.tv_detail_location)
        location.text = intent.getStringExtra("CIRCLE_LOCATION")
        if (location.text.isBlank()) location.text = "없음"

        schedule = findViewById(R.id.tv_detail_schedule)
        schedule.text = intent.getStringExtra("CIRCLE_SCHEDULE")
        if(schedule.text.isBlank()) schedule.text = "없음"

        phone = findViewById(R.id.tv_detail_phone)
        phone.text = intent.getStringExtra("CIRCLE_PHONE")
        if (phone.text.isBlank()) phone.text = "없음"

        owner = findViewById(R.id.tv_detail_owner)
        owner.text = intent.getStringExtra("CIRCLE_OWNER")

        val applyButton = findViewById<Button>(R.id.btn_detail_apply)
        applyButton.setOnClickListener {
            openBrowser(viewModel.circleContent.value?.applyLink)
        }
        applyButton.isClickable = false

        val officialSite = findViewById<TextView>(R.id.tv_detail_site)
        officialSite.setOnClickListener {
            openBrowser(viewModel.circleContent.value?.siteLink)
        }
        officialSite.isClickable = false

        val kakaoLink = findViewById<TextView>(R.id.tv_detail_kakao)
        kakaoLink.setOnClickListener {
            openBrowser(viewModel.circleContent.value?.kakaoLink)
        }
        kakaoLink.isClickable = false

        recruitState = findViewById(R.id.tv_detail_recruit_state)
        val recruit = intent.getBooleanExtra("CIRCLE_RECRUIT",true)
        if(recruit){
            recruitState.text = "# 모집중"
        }else{
            recruitState.text = "# 모집마감"
            applyButton.isEnabled = false
            applyButton.visibility = View.GONE
        }
        division = findViewById(R.id.tv_detail_division)
        division.text = "# ${intent.getStringExtra("CIRCLE_DIVISION")}"

        category = findViewById(R.id.tv_detail_category)
        category.text = "# ${intent.getStringExtra("CIRCLE_CATEGORY")}"

        description = findViewById(R.id.tv_detail_description)
        description.text = intent.getStringExtra("CIRCLE_DESCRIPTION")

        val posterIndex = findViewById<LinearLayout>(R.id.poster_index)
        posterIndex.visibility = View.GONE
        val posterViewPager = findViewById<ViewPager2>(R.id.poster_viewPager)
        val curPage = findViewById<TextView>(R.id.poster_curpage)
        val allPage = findViewById<TextView>(R.id.poster_allpage)
        posterViewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                curPage.text = (position + 1).toString()
            }
        })

        val circleId = intent.getIntExtra("CIRCLE_ID",0)
        viewModel.requestCircleDetail(circleId)
        Log.d("requestCircleDetail실행됨", "$circleId")
        viewModel.circleContent.observe(
            this,{
                //사진, 지원링크, 공식페이지, 카카오톡 불러오기.
                val photos = it.photos
                var photoCnt = 0
                if(photos.isNotEmpty()) {
                    photos.forEach {
                        if(it.photoType=="서브"){
                            imagesId.add(it.id)
                            photoCnt++
                        }
                    }
                    if(photoCnt != 0) {
                        allPage.text = photoCnt.toString()
                        posterIndex.visibility = View.VISIBLE
                    }else{
                        imagesId.add(-1)
                    }
                    val adapter = PosterImageViewerAdapter(this,imagesId)
                    posterViewPager.adapter = adapter

                }else if(photos.isEmpty()) {
                    imagesId.add(-1)
                    val adapter = PosterImageViewerAdapter(this,imagesId)
                    posterViewPager.adapter = adapter
                }
                applyButton.isClickable = true
                officialSite.isClickable = true
                kakaoLink.isClickable = true
            })


        phone.setOnClickListener {
            openCall(viewModel.circleContent.value?.phoneNumber)
        }

        val backButton = findViewById<ImageButton>(R.id.btn_back)
        backButton.setOnClickListener {
            finish()
        }
    }

    companion object{
        fun newIntent(context: Context, circleId : Int, circleName: String,recruit:Boolean,
                      location:String?, scheduleInfo:String?,phone:String?,owner:String?,
                      division:String, category:String, description:String): Intent {
            return Intent(context, CircleDetailActivity::class.java).apply {
                putExtra("CIRCLE_ID",circleId)
                putExtra("CIRCLE_NAME",circleName)
                putExtra("CIRCLE_RECRUIT",recruit)
                putExtra("CIRCLE_LOCATION",location)
                putExtra("CIRCLE_SCHEDULE",scheduleInfo)
                putExtra("CIRCLE_PHONE",phone)
                putExtra("CIRCLE_OWNER",owner)
                putExtra("CIRCLE_DIVISION",division)
                putExtra("CIRCLE_CATEGORY",category)
                putExtra("CIRCLE_DESCRIPTION",description)
            }
        }
    }

    private fun openBrowser(targetUrl : String?){

        if(targetUrl == null || targetUrl.isBlank()){
            showToastMsg("등록된 URL이 없습니다")
        }else{
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(targetUrl))
                startActivity(intent)
            }catch (e:Exception){
                e.printStackTrace()
                showToastMsg("잘못된 URL 입니다")
            }
        }
    }

    private fun openCall(phoneNumber:String?){
        if(phoneNumber == null || phoneNumber.isBlank()){
            showToastMsg("등록된 전화번호가 없습니다")
        }else{
            try {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${phoneNumber}"))
                startActivity(intent)
            }catch (e:Exception){
                e.printStackTrace()
                showToastMsg("잘못된 번호 입니다")
            }
        }
    }

    fun showToastMsg(msg:String){ Toast.makeText(this,msg, Toast.LENGTH_SHORT).show() }
}