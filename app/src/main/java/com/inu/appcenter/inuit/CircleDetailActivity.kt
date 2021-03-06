package com.inu.appcenter.inuit

import android.content.*
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.inu.appcenter.inuit.imageviewer.OnPosterClick
import com.inu.appcenter.inuit.imageviewer.PosterImageViewerAdapter
import com.inu.appcenter.inuit.imageviewer.PosterSlideImageViewer
import com.inu.appcenter.inuit.viewmodel.CircleDetailViewModel

class CircleDetailActivity : AppCompatActivity() ,OnPosterClick{

    private val viewModel by viewModels<CircleDetailViewModel>()

    private lateinit var circleNameTitle : TextView
    private lateinit var circleIntrodueTitle : TextView
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

        val circleName = intent.getStringExtra("CIRCLE_NAME")

        circleNameTitle = findViewById(R.id.tv_top_title_circle_name)
        circleNameTitle.text = circleName

        circleIntrodueTitle = findViewById(R.id.tv_mid_title_circle_introduce)
        circleIntrodueTitle.text = "$circleName 소개"

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
                    val adapter = PosterImageViewerAdapter(this,imagesId,this)
                    posterViewPager.adapter = adapter

                }else if(photos.isEmpty()) {
                    imagesId.add(-1)
                    val adapter = PosterImageViewerAdapter(this,imagesId,this)
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

        val menuButton = findViewById<ImageButton>(R.id.ibtn_menu_circle_detail)
        menuButton.setOnClickListener {
            val popupMenu = PopupMenu(this, menuButton)
            popupMenu.inflate(R.menu.menu_circle_detial)
            popupMenu.setOnMenuItemClickListener {
                if(it.itemId == R.id.menu_report_circle){
                    val circleName = intent.getStringExtra("CIRCLE_NAME")
                    val circleId = intent.getIntExtra("CIRCLE_ID",-1)
                    val emailIntent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("mailto:?subject=" + "<INUIT 신고 접수>" + "&body=" + "$circleName 신고(동아리 ID : $circleId)\n\n신고 사유:" + "&to=" + "2002lkw@naver.com")
                    }
                    try {
                        startActivity(Intent.createChooser(emailIntent,""))
                    } catch (ex: ActivityNotFoundException) {
                        Toast.makeText(
                            this,
                            "No email clients installed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    //startActivity(emailIntent)
                }
                false
            }
            popupMenu.show()
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
            showToastMsg("등록된 링크가 없어요")
        }else{
            try {
                if(targetUrl.contains("forms.gle")){
                    val intent = WebViewActivity.newIntent(this,targetUrl)
                    startActivity(intent)
                }else{
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(targetUrl))
                    startActivity(intent)
                }
            }catch (e:Exception){
                e.printStackTrace()
                showToastMsg("잘못된 링크입니다")
            }
        }
    }

    private fun openCall(phoneNumber:String?){
        if(phoneNumber == null || phoneNumber.isBlank()){
            showToastMsg("등록된 연락처가 없습니다")
        }else if(phoneNumber.contains("010") || phoneNumber.contains("032") || phoneNumber.contains("-")) {
            try {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${phoneNumber}"))
                startActivity(intent)
            }catch (e:Exception){
                e.printStackTrace()
                showToastMsg("잘못된 연락처입니다.")
            }
        }else if(phoneNumber.contains("@")){
            val emailIntent = Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto", phoneNumber, null))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "")
            startActivity(Intent.createChooser(emailIntent, ""))

        } else{
            val clipboard: ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", phoneNumber)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "연락처가 클립보드에 복사되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    fun showToastMsg(msg:String){ Toast.makeText(this,msg, Toast.LENGTH_SHORT).show() }

    override fun startPosterImageViewer(curIndex: Int) {
        if(imagesId[0] != -1){
            PosterSlideImageViewer.start(this,imagesId,curIndex)
        }
    }
}