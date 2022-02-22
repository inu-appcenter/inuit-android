package com.inu.appcenter.inuit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tv_signup = findViewById<TextView>(R.id.tv_signup)
        tv_signup.setOnClickListener {
            val intent = SingUpActivity.newIntent(this@LoginActivity)
            startActivity(intent)
        }

        val loginButton = findViewById<Button>(R.id.btn_login)
        loginButton.setOnClickListener {
            //로그인 성공시
            val intent = MyProfileActivity.newIntent(this@LoginActivity)
            startActivity(intent)
            //실패시 -> 토스트 메세지 출력.
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }
}