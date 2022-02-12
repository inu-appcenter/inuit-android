package com.inu.appcenter.inuit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.activity.viewModels
import com.inu.appcenter.viewmodel.ClubListViewModel

var login = false //임시, 추후에 로그인 정보는 뷰모델로 관리.

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<ClubListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_category : ImageButton = findViewById(R.id.imgBtn_category)
        btn_category.setOnClickListener(CategoryListener())

        val btn_myprofile : ImageButton = findViewById(R.id.imgBtn_myprofile)
        btn_myprofile.setOnClickListener(ProfileListener())

        viewModel.setDataNoCategory()
        Log.d("MainActivity","viewModel.setDataNoCategory() 실행됨")
    }

    inner class CategoryListener : View.OnClickListener{
        override fun onClick(view: View?) {
            val intent = CategoryActivity.newIntent(this@MainActivity)
            startActivity(intent)
        }
    }

    inner class ProfileListener : View.OnClickListener{
        override fun onClick(view: View?) {
            if(!login){
                val intent = LoginActivity.newIntent(this@MainActivity)
                startActivity(intent)
            }else{
                //내 프로필 액티비티 실행.
            }
        }
    }
}