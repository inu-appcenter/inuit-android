package com.inu.appcenter.inuit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import com.inu.appcenter.inuit.login.App
import com.inu.appcenter.inuit.util.LoadingDialog
import com.inu.appcenter.inuit.util.Utility
import com.inu.appcenter.inuit.viewmodel.FindAccountViewModel

class FindAccountActivity : AppCompatActivity() {

    private val viewModel by viewModels<FindAccountViewModel>()

    private lateinit var email : EditText
    private lateinit var emailButton : Button
    private lateinit var verifyTitle : TextView
    private lateinit var verifyCode : EditText
    private lateinit var verifyCodeButton: Button
    private lateinit var verifiedEmail : String

    private lateinit var loadingDialog: LoadingDialog
    private val correct = "inu.ac.kr"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_account)
        email = findViewById(R.id.et_email_find_account)
        emailButton = findViewById(R.id.btn_send_code_find_account)
        verifyTitle = findViewById(R.id.tv_title_code_find_account)
        verifyCode = findViewById(R.id.et_code_find_account)
        verifyCodeButton = findViewById(R.id.btn_code_verified_find_account)
        loadingDialog = LoadingDialog(this)

        verifyTitle.visibility = View.GONE
        verifyCode.visibility = View.GONE
        verifyCodeButton.visibility = View.GONE

        val cancelButton = findViewById<ImageButton>(R.id.btn_cancel)
        cancelButton.setOnClickListener {
            finish()
        }

        emailButton.setOnClickListener {
            if(isCorrectEmail()){
                requestVerifyCode()
            }else{
                showToastMsg(getString(R.string.msg_wrong_email))
            }

        }

        verifyCodeButton.setOnClickListener {
            checkCodeAndGetToken()
        }
    }

    companion object {
        fun newIntent(context: Context) : Intent {
            return Intent(context,FindAccountActivity::class.java)
        }
    }

    private fun requestVerifyCode(){
        loadingDialog.show()
        viewModel.requestVerifyCode(email.text.toString())
        viewModel.correctEmail.observe(
            this,
            {
                loadingDialog.dismiss()
                if (it == email.text.toString()){
                    showToastMsg(getString(R.string.msg_code_send))
                    showCodeLayout()
                    Utility.focusEditText(this,verifyCode)
                    emailButton.visibility = View.GONE
                    verifiedEmail = it
                }else if(it == "code not sent"){
                    showToastMsg(getString(R.string.find_account_code_not_sent))
                    Utility.focusEditText(this,email)
                }
            })
    }

    private fun checkCodeAndGetToken(){
        loadingDialog.show()
        viewModel.requestToken(verifiedEmail,verifyCode.text.toString())
        viewModel.token.observe(
            this,
            {
                if(it == "code is incorrect"){
                    showToastMsg(getString(R.string.msg_wrong_code))
                    Utility.focusEditText(this,verifyCode)
                    loadingDialog.dismiss()
                }else{ //성공적으로 토큰을 받았을 경우
                    loadingDialog.dismiss()
                    val intent = NewPasswordActivity.newIntent(this,it)
                    startActivity(intent)
                    finish()
                }
            })
    }

    private fun showCodeLayout(){
        verifyTitle.visibility = View.VISIBLE
        verifyCode.visibility = View.VISIBLE
        verifyCodeButton.visibility = View.VISIBLE
    }

    private fun isCorrectEmail(): Boolean { //형식상 일치하는지 판단.(inu.ac.kr로 끝나는지 확인)
        val inputEmail = email.text.toString()
        if (!inputEmail.contains('@')) {
            return false
        }

        val divEmail = inputEmail.split("@")
        Log.d("email", divEmail[1])
        return divEmail[1] == correct
    }

    fun showToastMsg(msg:String){ Toast.makeText(this,msg, Toast.LENGTH_SHORT).show() }
}