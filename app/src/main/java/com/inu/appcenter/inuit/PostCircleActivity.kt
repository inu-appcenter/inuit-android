package com.inu.appcenter.inuit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerSavePath
import com.esafirm.imagepicker.features.registerImagePicker
import com.esafirm.imagepicker.model.Image
import com.inu.appcenter.inuit.imageviewer.SlideImageViewer
import com.inu.appcenter.inuit.recycler.ImagePreviewAdapter

class PostCircleActivity : AppCompatActivity() {

    private val profileImage = arrayListOf<Image>()
    private val posterImage = arrayListOf<Image>()

    private lateinit var profileRecyclerView: RecyclerView
    private lateinit var profileImageAdapter : ImagePreviewAdapter
    private lateinit var addProfileImage : TextView

    private lateinit var posterRecyclerView: RecyclerView
    private lateinit var posterImageAdapter : ImagePreviewAdapter
    private lateinit var addPosterImage : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_circle)

        profileRecyclerView = findViewById(R.id.recycler_profile_image)
        profileRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        profileImageAdapter = ImagePreviewAdapter()
        profileRecyclerView.adapter = profileImageAdapter

        addProfileImage = findViewById(R.id.tv_post_circle_profile_image)
        addProfileImage.setOnClickListener {
            if(profileImageAdapter.itemsSize()==0){
                profilePickerLauncher.launch(profilePickerConfig)
            }else{
                showToastMsg(getString(R.string.post_profile_image_msg))
            }
        }

        posterRecyclerView = findViewById(R.id.recycler_poster_image)
        posterRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        posterImageAdapter = ImagePreviewAdapter()
        posterRecyclerView.adapter = posterImageAdapter

        addPosterImage = findViewById(R.id.tv_post_circle_poster_image)
        addPosterImage.setOnClickListener {
            if(posterImageAdapter.itemsSize() == 0){
                posterPickerLauncher.launch(posterPickerConfig)
            }else{
                showToastMsg(getString(R.string.post_poster_image_msg))
            }
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

    private val profilePickerConfig = ImagePickerConfig{
        savePath = ImagePickerSavePath("Camera")
        savePath = ImagePickerSavePath(Environment.getExternalStorageDirectory().path, isRelative = false)
        limit = 1
    }

    private val profilePickerLauncher = registerImagePicker { result: List<Image> ->

        result.forEach { image ->
            println(image)

            profileImageAdapter.addProfileImage(image.uri)
            profileImage.clear()
            profileImage.addAll(result)
            //SlideImageViewer.start(this,profileImage)
        }
    }

    private val posterPickerConfig = ImagePickerConfig{
        savePath = ImagePickerSavePath("Camera")
        savePath = ImagePickerSavePath(Environment.getExternalStorageDirectory().path, isRelative = false)
        limit = 1
    }

    private val posterPickerLauncher = registerImagePicker { result: List<Image> ->

        result.forEach { image ->
            println(image)

            posterImageAdapter.addProfileImage(image.uri)
            posterImage.clear()
            posterImage.addAll(result)
            //SlideImageViewer.start(this,posterImage)
        }
    }


    fun showToastMsg(msg:String){ Toast.makeText(this,msg, Toast.LENGTH_SHORT).show() }
}