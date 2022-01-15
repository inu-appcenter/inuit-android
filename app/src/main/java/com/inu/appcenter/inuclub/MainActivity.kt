package com.inu.appcenter.inuclub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.FragmentContainerView
import com.inu.appcenter.inuclub.fragment.AllClubListFragment
import com.inu.appcenter.inuclub.fragment.ClubListFrameFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var currentFragment = findViewById(R.id.fragmentContainerView) as FragmentContainerView

        if(currentFragment == null){
            val fragment = ClubListFrameFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainerView, fragment)
                .commit()
        }

    }


}