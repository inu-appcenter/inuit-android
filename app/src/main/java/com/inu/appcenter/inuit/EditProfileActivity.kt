package com.inu.appcenter.inuit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.inu.appcenter.inuit.login.App

class EditProfileActivity : AppCompatActivity() {

    lateinit var userEmail: TextView
    lateinit var newNickname : EditText
    lateinit var newPassword : EditText
    lateinit var newPasswordCheck : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val memberInfo = App.memberInfo

        userEmail = findViewById(R.id.tv_user_email_editprofile)
        newNickname = findViewById(R.id.et_new_nickname)
        newPassword = findViewById(R.id.et_new_password)
        newPasswordCheck = findViewById(R.id.et_new_password_check)

        userEmail.text = memberInfo?.email
        newNickname.setText(memberInfo?.nickname)
        val cancelButton = findViewById<ImageButton>(R.id.btn_cancel)
        cancelButton.setOnClickListener {
            finish()
        }

        val deleteButton = findViewById<ImageButton>(R.id.btn_delete_edittext)
        deleteButton.setOnClickListener {
            newNickname.text.clear()
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, EditProfileActivity::class.java)
        }
    }
}