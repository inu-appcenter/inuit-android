package com.inu.appcenter.inuit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.FragmentContainerView
import com.inu.appcenter.inuit.fragment.ClubListFrameFragment

var login = false //임시, 추후에 로그인 정보는 뷰모델로 관리.

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btn_category : ImageButton = findViewById(R.id.imgBtn_category)
        btn_category.setOnClickListener(CategoryListener())

        var btn_myprofile : ImageButton = findViewById(R.id.imgBtn_myprofile)
        btn_myprofile.setOnClickListener(ProfileListener())

        var currentFragment = findViewById<FragmentContainerView>(R.id.fragmentContainerView)
        if(currentFragment == null) setFragment()
    }

    fun setFragment(){
        val fragment = ClubListFrameFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainerView, fragment)
            .commit()
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