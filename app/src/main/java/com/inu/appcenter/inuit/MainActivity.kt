package com.inu.appcenter.inuit

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.inu.appcenter.inuit.viewmodel.ClubListViewModel

var login = false //임시, 추후에 로그인 정보는 뷰모델로 관리.

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<ClubListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.setDataNoCategory()
        Log.d("MainActivity","viewModel.setDataNoCategory() 실행됨")

        val categoryButton : ImageButton = findViewById(R.id.imgBtn_category)
        categoryButton.setOnClickListener{
            startCategoryActivity()
        }

        val search : LinearLayout = findViewById(R.id.searchGroup)
        search.setOnClickListener {
            startSearchActivity()
        }

        val profileButton : ImageButton = findViewById(R.id.imgBtn_myprofile)
        profileButton.setOnClickListener {
            startProfileActivity()
        }
    }

    private fun startCategoryActivity(){
        val intent = CategoryActivity.newIntent(this@MainActivity)
        startActivity(intent)
    }

    private fun startProfileActivity(){
        if(!login){
            startLoginActivity()
        }else{
            //내 프로필 액티비티 실행.
        }
    }

    private fun startLoginActivity(){
        val intent = LoginActivity.newIntent(this@MainActivity)
        startActivity(intent)
    }

    private fun startSearchActivity(){
        val intent = SearchActivity.newIntent(this@MainActivity)
        startActivity(intent)
    }

}