package com.inu.appcenter.inuit

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerSavePath
import com.esafirm.imagepicker.features.registerImagePicker
import com.esafirm.imagepicker.model.Image
import com.google.android.material.datepicker.MaterialDatePicker
import com.inu.appcenter.inuit.imageviewer.SlideImageViewer
import com.inu.appcenter.inuit.login.App
import com.inu.appcenter.inuit.recycler.OnPreviewImageClick
import com.inu.appcenter.inuit.recycler.PreviewImageAdapter
import com.inu.appcenter.inuit.retrofit.dto.CirclePostBody
import com.inu.appcenter.inuit.util.LoadingDialog
import com.inu.appcenter.inuit.util.Utility
import com.inu.appcenter.inuit.viewmodel.PostCircleViewModel
import com.wayne.constraintradiogroup.ConstraintRadioGroup
import com.wayne.constraintradiogroup.OnCheckedChangeListener
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PostCircleActivity : AppCompatActivity(), OnPreviewImageClick {

    private val viewModel by viewModels<PostCircleViewModel>()
    private var files  = ArrayList<MultipartBody.Part>()

    private val profileImage = arrayListOf<Image>()
    private val posterImage = arrayListOf<Image>()

    private val profileImageUrls = arrayListOf<String>()
    private val posterImageUrls = arrayListOf<String>()

    private lateinit var profileRecyclerView: RecyclerView
    private lateinit var profileImageAdapter : PreviewImageAdapter
    private lateinit var addProfileImage : TextView

    private lateinit var posterRecyclerView: RecyclerView
    private lateinit var posterImageAdapter : PreviewImageAdapter
    private lateinit var addPosterImage : TextView

    private lateinit var setRecruitSchedule : TextView
    private lateinit var dropDownRecruitSchedule : ImageButton
    private lateinit var isoStartDate : String
    private lateinit var isoEndDate : String

    // -- EditText --
    private lateinit var name : EditText
    private lateinit var oneLineIntroduce : EditText
    private lateinit var description : EditText
    private lateinit var location : EditText
    private lateinit var siteLink : EditText
    private lateinit var kakaoLink : EditText
    private lateinit var phone : EditText
    private lateinit var applyLink:EditText

    private lateinit var divisionGroup : RadioGroup
    private lateinit var categoryGroup : ConstraintRadioGroup
    private var division : String? = null
    private var category : String? = null
    private var information : String? = null

    private lateinit var loadingDialog: LoadingDialog

    private var isPatchMode : Boolean? = null
    private val postedPhotos = ArrayList<Int>()
    private var postedCircleId : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_circle)

        loadingDialog = LoadingDialog(this@PostCircleActivity)

        val isPatch = intent.getBooleanExtra("PATCH_MODE",false)
        if (isPatch){ //동아리 수정하기로 실행된 경우.
            val titleTextView = findViewById<TextView>(R.id.tv_post_circle_title)
            titleTextView.text = getString(R.string.top_title_patch_circle)

            isPatchMode = true
            postedCircleId = intent.getIntExtra("CIRCLE_ID",-1)
            loadingDialog.show()
            viewModel.getPostedCircleContent(postedCircleId!!)
            viewModel.circleContent.observe(this,
                {
                    name.setText(it.name)
                    oneLineIntroduce.setText(it.oneLineIntroduce)
                    description.setText(it.introduce)
                    location.setText(it.address)
                    siteLink.setText(it.siteLink)
                    kakaoLink.setText(it.kakaoLink)
                    phone.setText(it.phoneNumber)
                    applyLink.setText(it.applyLink)
                    division = it.division
                    category = it.category
                    setDivision(division!!)
                    setCategory(category!!)
                    if(it.recruitStartDate != null && it.recruitEndDate != null){
                        isoStartDate = it.recruitStartDate
                        isoEndDate = it.recruitStartDate
                        information = it.information
                        setRecruitSchedule.text = information
                    }else{
                        information = null
                    }
                    it.photos.forEach {
                        postedPhotos.add(it.id)
                    }
                    showToastMsg(getString(R.string.select_photo_again))
                    loadingDialog.dismiss()
                })
        }

        name = findViewById(R.id.et_post_circle_name)
        oneLineIntroduce = findViewById(R.id.et_post_circle_line_introduce)
        description = findViewById(R.id.et_post_circle_description)
        location = findViewById(R.id.et_post_circle_location)
        siteLink = findViewById(R.id.et_post_circle_site)
        kakaoLink = findViewById(R.id.et_post_circle_kakao)
        phone = findViewById(R.id.et_post_circle_phone)
        applyLink = findViewById(R.id.et_post_circle_apply_link)

        profileRecyclerView = findViewById(R.id.recycler_profile_image)
        addProfileImage = findViewById(R.id.tv_post_circle_profile_image)
        posterRecyclerView = findViewById(R.id.recycler_poster_image)
        addPosterImage = findViewById(R.id.tv_post_circle_poster_image)
        setRecruitSchedule = findViewById(R.id.tv_post_circle_recruit_schedule)
        dropDownRecruitSchedule = findViewById(R.id.ibtn_recruit_schedule)

        divisionGroup = findViewById(R.id.rg_division)
        categoryGroup = findViewById(R.id.rg_category)
        categoryGroup.checkedChangeListener = object : OnCheckedChangeListener {
            override fun onCheckedChanged(
                group: ConstraintRadioGroup,
                checkedButton: CompoundButton
            ) {
                when(checkedButton.id){
                    R.id.rb_academic -> category = getString(R.string.server_academic)
                    R.id.rb_exercise -> category = getString(R.string.server_exercise)
                    R.id.rb_culture -> category = getString(R.string.server_culture)
                    R.id.rb_service -> category = getString(R.string.server_service)
                    R.id.rb_religion -> category = getString(R.string.server_religion)
                    R.id.rb_etc -> category = getString(R.string.server_etc)
                    R.id.rb_hobby_exhibition ->category = getString(R.string.server_hobby_exhibition)
                    else -> category = getString(R.string.server_etc)
                }
            }
        }
        
        val scrollView = findViewById<ScrollView>(R.id.scrollview_post_circle)
        val topGroup = findViewById<ConstraintLayout>(R.id.top_button_group)
        scrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (!v.canScrollVertically(1) || !v.canScrollVertically(-1)){
                topGroup.elevation = 0f
            }else{
            topGroup.elevation = 10f
            }
        }

        profileRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        profileImageAdapter = PreviewImageAdapter(0,this)
        profileRecyclerView.adapter = profileImageAdapter

        addProfileImage.setOnClickListener {
            if(profileImageAdapter.itemsSize()==0){
                profilePickerLauncher.launch(profilePickerConfig)
            }else{
                showToastMsg(getString(R.string.post_profile_image_msg))
            }
        }

        posterRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        posterImageAdapter = PreviewImageAdapter(1,this)
        posterRecyclerView.adapter = posterImageAdapter


        addPosterImage.setOnClickListener {
            if(posterImageAdapter.itemsSize() < 10){
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
            cancelDialog()
        }
        
        val checkButton = findViewById<ImageButton>(R.id.btn_post_circle)
        checkButton.setOnClickListener {
            if(checkBeforePost()){
                if (isPatch){
                    deletePostedPhoto()
                    patchCircleData()
                }else{
                    postCircleData()
                }
            }
        }
    }

    override fun onBackPressed() {
        cancelDialog()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, PostCircleActivity::class.java)
        }

        fun newPatchIntent(context: Context, isPatchMode:Boolean?, circleId: Int): Intent{
            return Intent(context,PostCircleActivity::class.java).apply {
                putExtra("PATCH_MODE",isPatchMode)
                putExtra("CIRCLE_ID",circleId)
            }
        }
    }

    // -- 동아리 프로필 이미지피커 설정 --
    private val profilePickerConfig = ImagePickerConfig{
        isShowCamera = false
        isFolderMode = true
        savePath = ImagePickerSavePath("Camera")
        savePath = ImagePickerSavePath(Environment.getExternalStorageDirectory().path, isRelative = false)
        limit = 1
    }

    // -- 동아리 프로필 이미지피커 런쳐 --
    private val profilePickerLauncher = registerImagePicker { result: List<Image> ->
        profileImageUrls.clear()
        result.forEach { image ->
            println(image)

            profileImageAdapter.addImage(image)
            profileImage.clear()
            profileImage.addAll(result)
        }
    }

    // -- 동아리 포스터 이미지피커 설정 --
    private var posterPickerConfig = ImagePickerConfig{
        isShowCamera = false
        isFolderMode = true
        savePath = ImagePickerSavePath("Camera")
        savePath = ImagePickerSavePath(Environment.getExternalStorageDirectory().path, isRelative = false)
        limit = 10
    }

    // -- 동아리 포스터 이미지피커 런쳐 --
    private val posterPickerLauncher = registerImagePicker { result: List<Image> ->

        result.forEach { image ->
            println(image)

            if(posterImageAdapter.itemsSize()<10){
                posterImageAdapter.addImage(image)
                posterImage.add(image)
            }
            //posterImage.addAll(result)
        }
    }

    private fun pickDateRange(){
        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText(getString(R.string.post_circle_recruit_schedule_dialog_msg))
                .setTheme(R.style.DatePickerTheme)
                .build()
        dateRangePicker.addOnNegativeButtonClickListener{ dateRangePicker.dismiss() }
        dateRangePicker.addOnPositiveButtonClickListener {
            val startDate = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).format(it.first)
            val endDate = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).format(it.second)
            setRecruitSchedule.text = startDate + " ~ " + endDate
            information = setRecruitSchedule.text.toString()
            isoStartDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it.first) + "T00:00:00"
            isoEndDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it.second) + "T23:59:59"
            Log.d("isoDate", "startDate: $isoStartDate, endDate : $isoEndDate")
        }
        dateRangePicker.show(supportFragmentManager,dateRangePicker.toString())
    }

    private fun dropDownMenu(){
        val pop = PopupMenu(this, dropDownRecruitSchedule)
        menuInflater.inflate(R.menu.menu_recruit_schedule, pop.menu)
        pop.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu_select_day -> pickDateRange()
                R.id.menu_all_day_open -> {
                    setRecruitSchedule.text = it.title
                    information = it.title.toString()
                    isoStartDate = "2000-01-01T00:00:00"
                    isoEndDate = "2099-12-29T23:23:23"
                }
                R.id.menu_close -> {
                    setRecruitSchedule.text = it.title
                    information = it.title.toString()
                    isoStartDate = "2000-01-01T00:00:00"
                    isoEndDate = "2000-01-01T00:00:00"
                }
            }
            false
        }
        pop.show()
    }

    private fun addImagesToFiles(images: ArrayList<Image>){
        images.forEach {
            files.add(getCompressedFile(it.path)) //files.add(imageToFile(it.path))는 고화질 이미지 전송 안됨
        }
    }

    private fun getCompressedFile(photoPath: String) : MultipartBody.Part{
        val bitmap = getScaledBitmap(photoPath,this)
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
        val byteArray = stream.toByteArray()
        val bitmapBody = RequestBody.create(MediaType.parse("image/jpeg"),byteArray)
        val body = MultipartBody.Part.createFormData("files",photoPath,bitmapBody)
        return body
    }


    private fun imageToFile(photoPath:String) : MultipartBody.Part{
        val file = File(photoPath)
        val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file)
        val body = MultipartBody.Part.createFormData("files", file.name, requestFile)
        return body
    }

    private fun deletePostedPhoto(){
        loadingDialog.show()
        postedPhotos.forEach{
            viewModel.deletePhoto(App.prefs.token!!, postedCircleId!!,it)
        }
    }

    private fun patchCircleData(){
        val body = getCirclePostBody()
        viewModel.patchCircle(App.prefs.token!!,postedCircleId!!,body)
        viewModel.patchedCircleId.observe(
            this,{
                if(it == -1){ //서버 응답 실패
                    loadingDialog.dismiss()
                    showToastMsg("수정 실패")
                }else{
                    updateMemberInfo()
                    postPhotos(it)
                }
            }
        )
    }

    private fun postCircleData(){
        loadingDialog.show()
        val body = getCirclePostBody()
        viewModel.postCircle(App.prefs.token!!,body)
        viewModel.postedCircleId.observe(
            this,
            {
                //POST circle 실행 성공시 처리.
                if(it == -1){ //서버 응답 실패
                    loadingDialog.dismiss()
                    showToastMsg("등록 실패")
                }else{
                    updateMemberInfo()
                    postPhotos(it)
                }
            }
        )
    }

    private fun updateMemberInfo(){
        viewModel.getNewMemberInfo(App.prefs.token!!)
        viewModel.memberInfo.observe(
            this,
            {
                App.memberInfo = it //멤버 정보 갱신(내가 등록한 동아리, circle id에 값을 추가하기 위해서,동아리 삭제 반영하기 위해서)
            }
        )
    }

    private fun postPhotos(circleId:Int){

        if(profileImage.isEmpty() && posterImage.isEmpty()){
            loadingDialog.dismiss()
            showToastMsg("동아리가 등록되었습니다.")
            setResult(RESULT_OK)
            finish()
        }else if(profileImage.isEmpty() && posterImage.isNotEmpty()){
            addImagesToFiles(posterImage)
            viewModel.postPhotos(App.prefs.token!!,circleId,files)
            viewModel.postedImagesId.observe(
                this,
                {
                    if(it[0] == -1){
                        loadingDialog.dismiss()
                        showToastMsg("사진을 업로드하지 못했습니다.")
                    }else{
                        showToastMsg("동아리가 등록되었습니다.")
                        setResult(RESULT_OK)
                        finish()                    }
                }
            )
        }else{
            addImagesToFiles(profileImage)
            addImagesToFiles(posterImage)
            viewModel.postPhotos(App.prefs.token!!,circleId,files)
            viewModel.postedImagesId.observe(
                this,
                {
                    if(it[0] == -1){ //서버 응답 실패
                        loadingDialog.dismiss()
                        showToastMsg("사진을 업로드하지 못했습니다.")
                    }else{
                        setCircleProfile(circleId,it[0])
                    }
                }
            )
        }
    }

    private fun setCircleProfile(circleId: Int,photoId:Int){
        viewModel.setProfile(App.prefs.token!!,circleId,photoId)
        viewModel.profileImageId.observe(
            this,
            {
                loadingDialog.dismiss()
                if(photoId == it){
                    showToastMsg("동아리가 등록되었습니다.")
                    setResult(RESULT_OK)
                    finish()
                }else if(it == -1){ //서버 응답 실패
                    showToastMsg("프로필 사진 등록 실패")
                }
            }
        )
    }


    private fun getCirclePostBody(): CirclePostBody {
        return CirclePostBody(
            name.text.toString(),
            division!!,
            category!!,
            oneLineIntroduce.text.toString(),
            description.text.toString(),
            getRecruit(),
            setRecruitSchedule.text.toString(),
            isoStartDate,
            isoEndDate,
            location.text.toString(),
            siteLink.text.toString(),
            kakaoLink.text.toString(),
            phone.text.toString(),
            applyLink.text.toString())
            Log.d("category,division","$category,$division")
    }

    private fun getCircleDivision() :String?{
        when (divisionGroup.checkedRadioButtonId) {
            R.id.rb_main_circle -> division = getString(R.string.server_main_circle)
            R.id.rb_temp_circle -> division = getString(R.string.server_temp_circle)
            R.id.rb_small_circle -> division = getString(R.string.server_small_circle)
            else -> division = null
        }
        return division
    }

    private fun getRecruit():Boolean = setRecruitSchedule.text != "모집마감"

    private fun isNameEmpty() : Boolean = name.text.isEmpty()

    private fun isDivisionEmpty() : Boolean = getCircleDivision() == null

    private fun isCategoryEmpty() : Boolean = category == null

    private fun isIntroduceEmpty() : Boolean = oneLineIntroduce.text.isEmpty()

    private fun isDescriptionEmpty() : Boolean = description.text.isEmpty()

    private fun isRecruitInfoEmpty() : Boolean = information == null

    private fun checkBeforePost() : Boolean{
        var check = false
        when {
            isNameEmpty() -> {
                showToastMsg("동아리 이름을 입력해 주세요")
                Utility.focusEditText(this,name)
            }
            isIntroduceEmpty() -> {
                showToastMsg("한 줄 소개를 입력해 주세요")
                Utility.focusEditText(this,oneLineIntroduce)
            }
            isDivisionEmpty() -> {
                showToastMsg("동아리 유형을 선택해 주세요")
            }
            isCategoryEmpty() -> {
                showToastMsg("주제 키워드를 선택해 주세요")
            }
            isDescriptionEmpty() -> {
                showToastMsg("동아리 소개를 입력해 주세요")
                Utility.focusEditText(this,description)
            }
            isRecruitInfoEmpty() -> {
                showToastMsg("모집 일정을 선택해 주세요")
            }
            else -> {
                check = true
            }
        }
        return check
    }

    fun showToastMsg(msg:String){ Toast.makeText(this,msg, Toast.LENGTH_SHORT).show() }

    fun cancelDialog(){
        AlertDialog.Builder(this)
            .setTitle("상세페이지 작성 취소")
            .setMessage("입력한 내용이 사라집니다. \n정말로 취소하시겠습니까?")
            .setPositiveButton("확인") { dialog, which ->
                finish()
                super.onBackPressed()
                dialog.dismiss()
                Log.d("MyTag", "positive") }
            .setNegativeButton("취소") { dialog, which ->
                dialog.dismiss()
                Log.d("MyTag", "negative") }
            .create()
            .show()
    }

    override fun startProfileSlideImageViewer(curIndex:Int) { //0은 프로필 1은 포스터 및 추가 이미지
        SlideImageViewer.start(this, profileImageUrls)
    }

    override fun startPosterSlideImageViewer(curIndex: Int) {
        SlideImageViewer.start(this,posterImageUrls,curIndex)
    }

    override fun deletePosterImage(position: Int) {
        posterImage.removeAt(position)
    }

    private fun setDivision(division:String){
        when (division) {
            getString(R.string.server_main_circle) -> divisionGroup.check(R.id.rb_main_circle)
            getString(R.string.server_temp_circle) -> divisionGroup.check(R.id.rb_temp_circle)
            getString(R.string.server_small_circle) ->divisionGroup.check(R.id.rb_small_circle)
        }
    }

    private fun setCategory(category: String){
        when(category){
            getString(R.string.server_academic)->{
                val radioButton = findViewById<RadioButton>(R.id.rb_academic)
                radioButton.isChecked = true
            }
            getString(R.string.server_exercise)->{
                val radioButton = findViewById<RadioButton>(R.id.rb_exercise)
                radioButton.isChecked = true
            }
            getString(R.string.server_religion)->{
                val radioButton = findViewById<RadioButton>(R.id.rb_religion)
                radioButton.isChecked = true
            }
            getString(R.string.server_service)->{
                val radioButton = findViewById<RadioButton>(R.id.rb_service)
                radioButton.isChecked = true
            }
            getString(R.string.server_culture)->{
                val radioButton = findViewById<RadioButton>(R.id.rb_culture)
                radioButton.isChecked = true
            }
            getString(R.string.server_etc) -> {
                val radioButton = findViewById<RadioButton>(R.id.rb_etc)
                radioButton.isChecked = true
            }
            getString(R.string.server_hobby_exhibition) ->{
                val radioButton = findViewById<RadioButton>(R.id.rb_hobby_exhibition)
                radioButton.isChecked = true
            }
        }
    }
}