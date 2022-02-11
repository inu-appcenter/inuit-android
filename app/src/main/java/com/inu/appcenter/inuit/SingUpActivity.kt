package com.inu.appcenter.inuit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.*
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
        backButton.setOnClickListener {
            finish()
        }

        val sendCode = findViewById<TextView>(R.id.tv_send_certification)
        sendCode.setOnClickListener {
            outFocusEditText()

            if (!isCorrectEmail()) {
                // 이메일이 올바른 형식이 아닙니다.
                showToastMsg(getString(R.string.msg_wrong_email))

            }else{
                viewModel.postEmail(email.text.toString())
                //입력하신 이메일로 인증번호가 전송되었습니다.
                showToastMsg(getString(R.string.msg_code_send))
            }
        }

        val signUpButton = findViewById<Button>(R.id.btn_singup)
        signUpButton.setOnClickListener {

            if(password.text.toString() != passwordCheck.text.toString()) {
                //비밀번호가 일치하지 않습니다!
                showToastMsg(getString(R.string.msg_incorrect_password))
            }else{
                if (viewModel.isVerifiedEmail(email.text.toString(),certificationNum.text.toString())){
                    // 회원가입 성공, 닉네임, 이메일, 패스워드 데이터 전송
                } else{
                    //인증번호가 일치하지 않습니다.
                    showToastMsg(getString(R.string.msg_wrong_code))
                }
            }
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SingUpActivity::class.java)
        }
    }

    fun outFocusEditText(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(email.windowToken, 0)
    }

    fun isCorrectEmail():Boolean{
        var inputEmail = email.text.toString()
        var divEmail = inputEmail.split("@")
        Log.d("email","${divEmail[1]}")
        return divEmail[1] == correct
    }

    fun showToastMsg(msg:String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }
}