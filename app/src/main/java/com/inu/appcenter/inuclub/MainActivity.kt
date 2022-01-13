package com.inu.appcenter.inuclub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.inu.appcenter.inuclub.fragment.AllClubListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var fragment_container : FrameLayout = findViewById(R.id.fragmentContainerView)
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