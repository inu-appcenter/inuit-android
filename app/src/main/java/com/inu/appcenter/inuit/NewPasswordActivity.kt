package com.inu.appcenter.inuit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.inu.appcenter.inuit.retrofit.dto.MemberInfo
import com.inu.appcenter.inuit.util.LoadingDialog
import com.inu.appcenter.inuit.util.Utility
import com.inu.appcenter.inuit.viewmodel.NewPasswordViewModel

class NewPasswordActivity : AppCompatActivity() {

    private val viewModel by viewModels<NewPasswordViewModel>()
    private lateinit var memberInfo: MemberInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_password)

        val loadingDialog = LoadingDialog(this)
        loadingDialog.show()

        val token = intent.getStringExtra("FIND_ACCOUNT_TOKEN")
        viewModel.requestMemberInfo(token!!)
        viewModel.memberInfo.observe(
            this,
            {
                loadingDialog.dismiss()
                if (it == null){
                    showToastMsg(getString(R.string.login_server_error))
                    finish()
                }else{
                    memberInfo = it
                }
            })

        val password = findViewById<EditText>(R.id.et_new_password_find_account)
        val passwordCheck = findViewById<EditText>(R.id.et_new_password_check_find_account)
        val checkButton = findViewById<ImageButton>(R.id.btn_new_password)
        checkButton.setOnClickListener {
            loadingDialog.show()
            if(password.text.toString() == passwordCheck.text.toString()){
                viewModel.patchNewPassword(token,memberInfo.nickname,password.text.toString())
                viewModel.responseId.observe(this,
                    {
                        loadingDialog.dismiss()
                        if(memberInfo.id == it){
                            showToastMsg(getString(R.string.find_account_new_password_success))
                            finish()
                        }else{
                            showToastMsg(getString(R.string.login_server_error))
                        }
                    })
            }else{
                loadingDialog.dismiss()
                showToastMsg(getString(R.string.msg_incorrect_password))
                Utility.focusEditText(this, passwordCheck)
            }
        }

        val cancelButton = findViewById<ImageButton>(R.id.btn_cancel)
        cancelButton.setOnClickListener {
            finish()
        }

    }

    companion object{
        fun newIntent(context:Context,token:String) : Intent {
            return Intent(context, NewPasswordActivity::class.java).apply {
                putExtra("FIND_ACCOUNT_TOKEN",token)
            }
        }
    }

    fun showToastMsg(msg:String){ Toast.makeText(this,msg, Toast.LENGTH_SHORT).show() }
}