package com.inu.appcenter.inuit

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemClock.sleep(500)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}