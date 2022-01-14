package com.inu.appcenter.inuclub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.FragmentContainerView
import com.inu.appcenter.inuclub.fragment.AllClubListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var fragment_container = findViewById(R.id.fragmentContainerView) as FragmentContainerView
        if (fragment_container != null){
            if(savedInstanceState != null)
                return
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainerView, AllClubListFragment())
                .addToBackStack(null)
                .commit()
        }
    }


}