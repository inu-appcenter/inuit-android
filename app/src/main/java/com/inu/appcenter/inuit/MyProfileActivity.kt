package com.inu.appcenter.inuit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerSavePath
import com.esafirm.imagepicker.features.registerImagePicker
import com.esafirm.imagepicker.model.Image
import com.inu.appcenter.inuit.recycler.MyClubListAdapter

class MyProfileActivity : AppCompatActivity() {

    private lateinit var profileImage : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        val backButton = findViewById<ImageButton>(R.id.btn_back)
        backButton.setOnClickListener {
            finish()
        }

        val editMyProfile = findViewById<TextView>(R.id.change_my_profile)
        editMyProfile.setOnClickListener {
            val intent = EditProfileActivity.newIntent(this@MyProfileActivity)
            startActivity(intent)
        }

        val recycler_myclub_List = findViewById<RecyclerView>(R.id.recycler_myclub_list)
        recycler_myclub_List.layoutManager = LinearLayoutManager(this)
        val adapter = MyClubListAdapter()
        recycler_myclub_List.adapter = adapter
        adapter.setSampleData()

        //ImagePicker
        val config = ImagePickerConfig{
            savePath = ImagePickerSavePath("Camera")
            savePath = ImagePickerSavePath(Environment.getExternalStorageDirectory().path, isRelative = false)
            limit = 1
        }

        val launcher = registerImagePicker { result: List<Image> ->

            result.forEach { image ->
                println(image)
                Glide.with(this)
                    .load(image.uri)
                    .centerCrop()
                    .into(profileImage)
            }
        }

        profileImage = findViewById(R.id.iv_profile)
        profileImage.setOnClickListener {
            launcher.launch(config)
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MyProfileActivity::class.java)
        }
    }
}