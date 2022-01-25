package com.inu.appcenter.inuit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.FragmentContainerView
import com.inu.appcenter.inuit.fragment.ClubListFrameFragment

class MainActivity : AppCompatActivity() {

    private var login = false
    private lateinit var btn_category : ImageButton
    private lateinit var btn_myprofile : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_category = findViewById(R.id.imgBtn_category)
        btn_category.setOnClickListener(CategoryListener())

        btn_myprofile = findViewById(R.id.imgBtn_myprofile)
        btn_myprofile.setOnClickListener(ProfileListener())

        var currentFragment = findViewById<FragmentContainerView>(R.id.fragmentContainerView)

        if(currentFragment == null){
            val fragment = ClubListFrameFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainerView, fragment)
                .commit()
        }
    }

    inner class CategoryListener : View.OnClickListener{
        override fun onClick(view: View?) {
            //카테고리 액티비티 실행
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