package com.inu.appcenter.inuit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentContainerView
import com.inu.appcenter.inuit.fragment.ClubListFrameFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var currentFragment = findViewById<FragmentContainerView>(R.id.fragmentContainerView)

        if(currentFragment == null){
            val fragment = ClubListFrameFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainerView, fragment)
                .commit()
        }

    }
}