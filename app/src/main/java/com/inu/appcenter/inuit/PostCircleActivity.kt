package com.inu.appcenter.inuit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerSavePath
import com.esafirm.imagepicker.features.registerImagePicker
import com.esafirm.imagepicker.model.Image
import com.google.android.material.datepicker.MaterialDatePicker
import com.inu.appcenter.inuit.recycler.ImagePreviewAdapter
import java.text.SimpleDateFormat
import java.util.*

class PostCircleActivity : AppCompatActivity() {

    private val profileImage = arrayListOf<Image>()
    private val posterImage = arrayListOf<Image>()

    private lateinit var profileRecyclerView: RecyclerView
    private lateinit var profileImageAdapter : ImagePreviewAdapter
    private lateinit var addProfileImage : TextView

    private lateinit var posterRecyclerView: RecyclerView
    private lateinit var posterImageAdapter : ImagePreviewAdapter
    private lateinit var addPosterImage : TextView

    private lateinit var setRecruitSchedule : TextView
    private lateinit var dropDownRecruitSchedule : ImageButton

    private lateinit var applyLink:TextView
    private lateinit var applyConstraint : ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_circle)

        profileRecyclerView = findViewById(R.id.recycler_profile_image)
        addProfileImage = findViewById(R.id.tv_post_circle_profile_image)
        posterRecyclerView = findViewById(R.id.recycler_poster_image)
        addPosterImage = findViewById(R.id.tv_post_circle_poster_image)
        setRecruitSchedule = findViewById(R.id.tv_post_circle_recruit_schedule)
        dropDownRecruitSchedule = findViewById(R.id.ibtn_recruit_schedule)
        applyLink = findViewById(R.id.tv_title_circle_apply)
        applyConstraint = findViewById(R.id.et_post_apply_group)

        profileRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        profileImageAdapter = ImagePreviewAdapter()
        profileRecyclerView.adapter = profileImageAdapter

        addProfileImage.setOnClickListener {
            if(profileImageAdapter.itemsSize()==0){
                profilePickerLauncher.launch(profilePickerConfig)
            }else{
                showToastMsg(getString(R.string.post_profile_image_msg))
            }
        }

        posterRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        posterImageAdapter = ImagePreviewAdapter()
        posterRecyclerView.adapter = posterImageAdapter


        addPosterImage.setOnClickListener {
            if(posterImageAdapter.itemsSize() == 0){
                posterPickerLauncher.launch(posterPickerConfig)
            }else{
                showToastMsg(getString(R.string.post_poster_image_msg))
            }
        }

        setRecruitSchedule.setOnClickListener {
            pickDateRange()
        }

        dropDownRecruitSchedule.setOnClickListener {
            dropDownMenu()
        }

        val backButton = findViewById<ImageButton>(R.id.btn_cancel_post_circle)
        backButton.setOnClickListener {
            finish()
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, PostCircleActivity::class.java)
        }
    }

    // -- 동아리 프로필 이미지피커 설정 --
    private val profilePickerConfig = ImagePickerConfig{
        savePath = ImagePickerSavePath("Camera")
        savePath = ImagePickerSavePath(Environment.getExternalStorageDirectory().path, isRelative = false)
        limit = 1
    }

    // -- 동아리 프로필 이미지피커 런쳐 --
    private val profilePickerLauncher = registerImagePicker { result: List<Image> ->

        result.forEach { image ->
            println(image)

            profileImageAdapter.addImage(image)
            profileImage.clear()
            profileImage.addAll(result)
        }
    }

    // -- 동아리 포스터 이미지피커 설정 --
    private val posterPickerConfig = ImagePickerConfig{
        savePath = ImagePickerSavePath("Camera")
        savePath = ImagePickerSavePath(Environment.getExternalStorageDirectory().path, isRelative = false)
        limit = 1
    }

    // -- 동아리 포스터 이미지피커 런쳐 --
    private val posterPickerLauncher = registerImagePicker { result: List<Image> ->

        result.forEach { image ->
            println(image)

            posterImageAdapter.addImage(image)
            posterImage.clear()
            posterImage.addAll(result)
        }
    }

    private fun pickDateRange(){
        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText(getString(R.string.post_circle_recruit_schedule_dialog_msg))
                .build()
        dateRangePicker.addOnNegativeButtonClickListener{ dateRangePicker.dismiss() }
        dateRangePicker.addOnPositiveButtonClickListener {
            val startDate = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).format(it.first)
            val endDate = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).format(it.second)
            setRecruitSchedule.text = startDate + " ~ " + endDate
            Log.d("test", "startDate: $startDate, endDate : $endDate")
        }
        dateRangePicker.show(supportFragmentManager,dateRangePicker.toString())
    }

    private fun dropDownMenu(){
        val pop = PopupMenu(this, dropDownRecruitSchedule)
        menuInflater.inflate(R.menu.menu_recruit_schedule, pop.menu)
        pop.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu_select_day -> pickDateRange()
                R.id.menu_all_day_open -> setRecruitSchedule.text = it.title
                R.id.menu_close -> setRecruitSchedule.text = it.title
            }
            false
        }
        pop.show()
    }

    fun showToastMsg(msg:String){ Toast.makeText(this,msg, Toast.LENGTH_SHORT).show() }
}