package com.inu.appcenter.inuit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CircleDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle_detail)
    }

    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context, CircleDetailActivity::class.java)
        }
    }
}