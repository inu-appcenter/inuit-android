package com.inu.appcenter.inuit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.inu.appcenter.inuit.login.App
import com.inu.appcenter.inuit.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email = findViewById<EditText>(R.id.et_email)
        val password = findViewById<EditText>(R.id.et_password)

        val tv_signup = findViewById<TextView>(R.id.tv_signup)
        tv_signup.setOnClickListener {
            val intent = SignUpActivity.newIntent(this@LoginActivity)
            startActivity(intent)
        }

        val loginButton = findViewById<Button>(R.id.btn_login)
        loginButton.setOnClickListener {

            if(Utility.istNetworkConnected(this)) {
                viewModel.requestLogin(email.text.toString(), password.text.toString())
                viewModel.token.observe(this,
                    {
                        when (it) {
                            "not registered email" -> {
                                showToastMsg(getString(R.string.login_not_registered_email))
                            }
                            "incorrect password" -> {
                                showToastMsg(getString(R.string.login_incorrect_password))
                            }
                            "server error" -> {
                                showToastMsg(getString(R.string.login_server_error))
                            }
                            else -> { //토큰을 정상적으로 받았을 때.
                                App.prefs.token = it
                                App.nowLogin = true
                                showToastMsg("로그인 성공")
                                finish()
                            }
                        }
                    })
            }else{
                showToastMsg(getString(R.string.internet_not_connected))
            }
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    fun showToastMsg(msg:String){ Toast.makeText(this,msg,Toast.LENGTH_SHORT).show() }
}