package com.inu.appcenter.inuit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import com.inu.appcenter.inuit.retrofit.Email
import com.inu.appcenter.viewmodel.ClubListViewModel
import com.inu.appcenter.viewmodel.SignUpViewModel

class SingUpActivity : AppCompatActivity() {

    private val viewModel by viewModels<SignUpViewModel>()

    private lateinit var nickname : EditText
    private lateinit var email : EditText
    private lateinit var certificationNum : EditText
    private lateinit var password : EditText
    private lateinit var passwordCheck : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        nickname = findViewById(R.id.et_nickname)
        email = findViewById(R.id.et_singup_email)
        certificationNum = findViewById(R.id.et_certification)
        password = findViewById(R.id.et_password_signup)
        passwordCheck = findViewById(R.id.et_password_check)

        val backButton = findViewById<ImageButton>(R.id.btn_back)
        backButton.setOnClickListener {
            finish()
        }

        val sendCode = findViewById<TextView>(R.id.tv_send_certification)
        sendCode.setOnClickListener {
            if (viewModel.isCorrectEmail(email.text.toString())) {
                // 인증번호가 메일로 전송되었습니다.
            }else{
                // 이메일이 올바른 형식이 아닙니다.
            }
        }

        val signUpButton = findViewById<Button>(R.id.btn_singup)
        signUpButton.setOnClickListener {

        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SingUpActivity::class.java)
        }
    }

    fun isSame() : Boolean = password.text == passwordCheck.text



}