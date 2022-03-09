package com.inu.appcenter.inuit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView

class FindAccountActivity : AppCompatActivity() {

    private lateinit var email : EditText
    private lateinit var emailButton : Button
    private lateinit var verifyTitle : TextView
    private lateinit var verifyCode : EditText
    private lateinit var verifyCodeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_account)
        email = findViewById(R.id.et_email_find_account)
        emailButton = findViewById(R.id.btn_send_code_find_account)
        verifyTitle = findViewById(R.id.tv_title_code_find_account)
        verifyCode = findViewById(R.id.et_code_find_account)
        verifyCodeButton = findViewById(R.id.btn_code_verified_find_account)

        verifyTitle.visibility = View.GONE
        verifyCode.visibility = View.GONE
        verifyCodeButton.visibility = View.GONE

        val cancelButton = findViewById<ImageButton>(R.id.btn_cancel)
        cancelButton.setOnClickListener {
            finish()
        }
    }

    companion object {
        fun newIntent(context: Context) : Intent {
            return Intent(context,FindAccountActivity::class.java)
        }
    }
}