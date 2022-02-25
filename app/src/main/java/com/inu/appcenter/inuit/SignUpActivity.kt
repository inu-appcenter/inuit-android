package com.inu.appcenter.inuit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import com.airbnb.lottie.LottieAnimationView
import com.inu.appcenter.inuit.viewmodel.SignUpViewModel

class SignUpActivity : AppCompatActivity() {

    private val viewModel by viewModels<SignUpViewModel>()

    private lateinit var nickname : EditText
    private lateinit var email : EditText
    private lateinit var certificationNum : EditText
    private lateinit var password : EditText
    private lateinit var passwordCheck : EditText
    private lateinit var animation : LottieAnimationView

    private val correct = "inu.ac.kr"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        nickname = findViewById(R.id.et_nickname)
        email = findViewById(R.id.et_singup_email)
        certificationNum = findViewById(R.id.et_certification)
        password = findViewById(R.id.et_password_signup)
        passwordCheck = findViewById(R.id.et_password_check)
        animation = findViewById(R.id.loading_signup)
        pauseLoading()

        val backButton = findViewById<ImageButton>(R.id.btn_back)
        backButton.setOnClickListener { finish() }

        val sendCertification = findViewById<TextView>(R.id.tv_send_certification)
        sendCertification.setOnClickListener {
            Utility.outFocusEditText(this,email) //키보드 내리기
            sendCode() //이메일이 inu.ac.kr로 끝난다면 인증번호 전송
        }

        val signUpButton = findViewById<Button>(R.id.btn_singup)
        signUpButton.setOnClickListener {
            signUp()
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SignUpActivity::class.java)
        }
    }

    private fun isEmptyNickName(): Boolean = nickname.text.isEmpty()

    private fun isEmptyEmail(): Boolean = email.text.isEmpty()

    private fun isCorrectEmail(): Boolean{ //형식상 일치하는지 판단.(inu.ac.kr로 끝나는지 확인)
        val inputEmail = email.text.toString()
        if(!inputEmail.contains('@')){ return false }

        val divEmail = inputEmail.split("@")
        Log.d("email", divEmail[1])
        return divEmail[1] == correct
    }

    private fun isRegisteredEmail() : Boolean = viewModel.correctEmail == email.text

    private fun isEmptyPassword(): Boolean = password.text.isEmpty()

    private fun isCorrectPassword(): Boolean = password.text.toString() == passwordCheck.text.toString()

    private fun sendCode(){
        if (!Utility.istNetworkConnected(this)){
            showToastMsg(getString(R.string.internet_not_connected))
        }
        else if (isCorrectEmail()) {
            emailCountDown.start()
            startLoading()
            viewModel.postEmail(email.text.toString())
            viewModel.correctEmail.observe(
                this,
                {
                    pauseLoading()
                    emailCountDown.cancel()
                    if (it == email.text.toString()){
                        Utility.focusEditText(this,certificationNum)
                        showToastMsg(getString(R.string.msg_code_send))
                    }else if(it == "code not sent"){
                        showToastMsg(getString(R.string.msg_code_not_sent))
                        Utility.focusEditText(this,email)
                    }
                })
        }else{
            showToastMsg(getString(R.string.msg_wrong_email))
            Utility.focusEditText(this,email)
        }
    }

    private fun signUp(){

        if(!Utility.istNetworkConnected(this)){
            showToastMsg(getString(R.string.internet_not_connected))
        }
        else if(isEmptyNickName()){
            showToastMsg(getString(R.string.msg_empty_nickname)) //닉네임을 입력해주세요.
            Utility.focusEditText(this,nickname)
        }else if(isEmptyEmail()){
            showToastMsg(getString(R.string.msg_empty_email)) //이메일을 입력해주세요.
            Utility.focusEditText(this,email)
        }else if(!isCorrectEmail()){
            showToastMsg(getString(R.string.msg_wrong_email)) // 올바르지 않은 이메일 주소입니다.
            Utility.focusEditText(this,email)
        }else if(isEmptyPassword()){
            showToastMsg(getString(R.string.msg_empty_password))
            Utility.focusEditText(this,password)
        }
        else if(!isCorrectPassword()) {
            showToastMsg(getString(R.string.msg_incorrect_password)) //비밀번호가 일치하지 않습니다!
            Utility.focusEditText(this,passwordCheck)
        }else if(isRegisteredEmail()){
            showToastMsg(getString(R.string.msg_registered_email))
            Utility.focusEditText(this,email)
        }
        else{
            singUpCountDown.start()
            startLoading()
            viewModel.verifiedEmailResponse(email.text.toString(),certificationNum.text.toString())
            viewModel.verifiedCode.observe(
                this,
                {
                    pauseLoading()
                    singUpCountDown.cancel()
                    if(it == certificationNum.text.toString()){
                        // 회원가입 성공, 닉네임, 이메일, 패스워드 데이터 전송
                        viewModel.registerMember(email.text.toString(),nickname.text.toString(),password.text.toString())
                        showToastMsg("회원가입 성공")
                        finish()
                    }else if(it == "code is incorrect"){
                        showToastMsg(getString(R.string.msg_wrong_code))
                        Utility.focusEditText(this,certificationNum)
                    }
                })
        }
    }

    fun showToastMsg(msg:String){ Toast.makeText(this,msg,Toast.LENGTH_SHORT).show() }

    private fun pauseLoading(){
        animation.visibility = View.GONE
        animation.pauseAnimation()
    }

    fun startLoading(){
        animation.visibility = View.VISIBLE
        animation.playAnimation()
    }

    private val emailCountDown: CountDownTimer = object : CountDownTimer(9250, 500) {
        override fun onTick(millisUntilFinished: Long) {}
        override fun onFinish() {
            showToastMsg(getString(R.string.msg_code_not_sent))
            Utility.focusEditText(this@SignUpActivity,email)
        }
    }

    private val singUpCountDown: CountDownTimer = object : CountDownTimer(5250, 500) {
        override fun onTick(millisUntilFinished: Long) {}
        override fun onFinish() {
            showToastMsg(getString(R.string.msg_wrong_code))
            Utility.focusEditText(this@SignUpActivity,certificationNum)
        }
    }
}
