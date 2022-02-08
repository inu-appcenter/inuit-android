package com.inu.appcenter.inuit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels

class SelectedCategoryActivity : AppCompatActivity(){

    private val viewModel by viewModels<InuitViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_category)

        val backButton = findViewById<ImageButton>(R.id.btn_back)
        backButton.setOnClickListener {
            finish()
        }

        val tv_category = findViewById<TextView>(R.id.tv_top_title)
        tv_category.text = intent.getStringExtra("SELECTED_CATEGORY")

        val btn_myprofile = findViewById<ImageButton>(R.id.imgBtn_myprofile)
        btn_myprofile.setOnClickListener(ProfileListener())

        viewModel.category = intent.getStringExtra("SERVER_CATEGORY").toString()
        viewModel.isSelectedCategoryActivity = true
        viewModel.setDataWithCategory()
    }

    companion object {
        fun newIntent(context: Context, selectedCategory:String, serverCategory: String): Intent {
            return Intent(context, SelectedCategoryActivity::class.java).apply {
                putExtra("SELECTED_CATEGORY",selectedCategory)
                putExtra("SERVER_CATEGORY",serverCategory)
            }
        }
    }

    inner class ProfileListener : View.OnClickListener{
        override fun onClick(view: View?) {
            if(!login){
                val intent = LoginActivity.newIntent(this@SelectedCategoryActivity)
                startActivity(intent)
            }else{
                //내 프로필 액티비티 실행.
            }
        }
    }
}