package com.inu.appcenter.inuit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.activity.viewModels
import com.inu.appcenter.viewmodel.SignUpViewModel

class SingUpActivity : AppCompatActivity() {

    private val viewModel by viewModels<SignUpViewModel>()

    private lateinit var nickname : EditText
    private lateinit var email : EditText
    private lateinit var certificationNum : EditText
    private lateinit var password : EditText
    private lateinit var passwordCheck : EditText

    private val correct = "inu.ac.kr"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        nickname = findViewById(R.id.et_nickname)
        email = findViewById(R.id.et_singup_email)
        certificationNum = findViewById(R.id.et_certification)
        password = findViewById(R.id.et_password_signup)
        passwordCheck = findViewById(R.id.et_password_check)

        val backButton = findViewById<ImageButton>(R.id.btn_back)
        backButton.setOnClickListener { finish() }

        val sendCertification = findViewById<TextView>(R.id.tv_send_certification)
        sendCertification.setOnClickListener {
            outFocusEditText() //키보드 내리기
            sendCode()
        }

        val signUpButton = findViewById<Button>(R.id.btn_singup)
        signUpButton.setOnClickListener { signUp() }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SingUpActivity::class.java)
        }
    }

    private fun isEmptyNickName(): Boolean = nickname.text.isEmpty()

    private fun isEmptyEmail(): Boolean = email.text.isEmpty()

    private fun isCorrectEmail(): Boolean{
        val inputEmail = email.text.toString()
        if(!inputEmail.contains('@')){ return false }

        val divEmail = inputEmail.split("@")
        Log.d("email", divEmail[1])
        return divEmail[1] == correct
    }

    private fun isEmptyPassword(): Boolean = password.text.isEmpty()

    private fun isCorrectPassword(): Boolean = password.text.toString() == passwordCheck.text.toString()

    private fun sendCode(){
        if (isCorrectEmail()) {
            viewModel.postEmail(email.text.toString())
            showToastMsg(getString(R.string.msg_code_send)) //입력하신 이메일로 인증번호가 전송되었습니다.
        }else{
            showToastMsg(getString(R.string.msg_wrong_email)) // 올바르지 않은 이메일 주소입니다.
        }
    }

    private fun signUp(){
        if(isEmptyNickName()){
            showToastMsg(getString(R.string.msg_empty_nickname)) //닉네임을 입력해주세요.
        }else if(isEmptyEmail()){
            showToastMsg(getString(R.string.msg_empty_email)) //이메일을 입력해주세요.
        }else if(!isCorrectEmail()){
            showToastMsg(getString(R.string.msg_wrong_email)) // 올바르지 않은 이메일 주소입니다.
        }else if(isEmptyPassword()){
            showToastMsg(getString(R.string.msg_empty_password))
        }
        else if(!isCorrectPassword()) {
            showToastMsg(getString(R.string.msg_incorrect_password)) //비밀번호가 일치하지 않습니다!
        }else{
            if (viewModel.isVerifiedEmail(email.text.toString(),certificationNum.text.toString())){
                // 회원가입 성공, 닉네임, 이메일, 패스워드 데이터 전송
            } else{
                showToastMsg(getString(R.string.msg_wrong_code)) //인증번호가 일치하지 않습니다.
            }
        }
    }

    fun showToastMsg(msg:String){ Toast.makeText(this,msg,Toast.LENGTH_SHORT).show() }

    private fun outFocusEditText(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(email.windowToken, 0)
    }
}