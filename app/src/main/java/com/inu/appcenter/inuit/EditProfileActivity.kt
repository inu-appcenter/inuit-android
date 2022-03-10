package com.inu.appcenter.inuit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.inu.appcenter.inuit.login.App
import com.inu.appcenter.inuit.retrofit.dto.MemberInfo
import com.inu.appcenter.inuit.viewmodel.EditProfileViewModel

class EditProfileActivity : AppCompatActivity() {

    private val viewModel by viewModels<EditProfileViewModel>()

    lateinit var userEmail: TextView
    lateinit var newNickname : EditText
    lateinit var newPassword : EditText
    lateinit var newPasswordCheck : EditText

    lateinit var memberInfo : MemberInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        memberInfo = App.memberInfo!!

        userEmail = findViewById(R.id.tv_user_email_editprofile)
        newNickname = findViewById(R.id.et_new_nickname)
        newPassword = findViewById(R.id.et_new_password)
        newPasswordCheck = findViewById(R.id.et_new_password_check)

        userEmail.text = memberInfo?.email
        newNickname.setText(memberInfo?.nickname)

        val deleteButton = findViewById<ImageButton>(R.id.btn_delete_edittext)
        deleteButton.setOnClickListener {
            newNickname.text.clear()
        }

        val cancelButton = findViewById<ImageButton>(R.id.btn_cancel)
        cancelButton.setOnClickListener {
            finish()
        }

        val checkButton = findViewById<ImageButton>(R.id.btn_edit_profile)
        checkButton.setOnClickListener {
            if(newNickname.text.isEmpty()){
                showToastMsg(getString(R.string.msg_empty_nickname))
            }else if(newPassword.text.isEmpty()){
                showToastMsg(getString(R.string.msg_empty_password))
            }else if(newPassword.text.toString() != newPasswordCheck.text.toString()){
                showToastMsg(getString(R.string.msg_incorrect_password))
            }else{
                editMyProfile()
            }
        }

        val logout = findViewById<TextView>(R.id.tv_btn_logout)
        logout.setOnClickListener {
            App.nowLogin = false
            App.memberInfo = null
            App.prefs.token = null
            setResult(RESULT_OK)
            Toast.makeText(this,"로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }

        val deleteMyProfile  = findViewById<TextView>(R.id.tv_delete_profile)
        deleteMyProfile.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.delete_profile))
                .setMessage(getString(R.string.check_delete_profile))
                .setPositiveButton("확인") { dialog, which ->
                    deleteProfileProcess()
                    dialog.dismiss()
                    Log.d("MyTag", "positive") }
                .setNegativeButton("취소") { dialog, which ->
                    dialog.dismiss()
                    Log.d("MyTag", "negative") }
                .create()
                .show()
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, EditProfileActivity::class.java)
        }
    }

    private fun editMyProfile(){
        viewModel.requestEditMyProfile(App.prefs.token!!,newNickname.text.toString(),newPassword.text.toString())
        viewModel.responseId.observe(
            this,
            {
                if(memberInfo?.id == it){ //회원정보 수정 성공
                    App.nowLogin = false
                    App.memberInfo = null
                    App.prefs.token = null
                    setResult(RESULT_OK)
                    Toast.makeText(this,"회원정보 수정 성공", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        )
    }

    private fun deleteProfileProcess(){
        if(App.memberInfo?.circleId != null){
            viewModel.deleteCircle(App.prefs.token!!,App.memberInfo?.circleId!!)
            viewModel.deleteCircleId.observe(
                this,
                {
                    if(it == App.memberInfo?.circleId){
                        deleteMyProfile()
                    }
                })
        }else{
            deleteMyProfile()
        }
    }

    private fun deleteMyProfile(){
        viewModel.requestDeleteMyProfile(App.prefs.token!!)
        viewModel.deletedId.observe(
            this,
            {
                if(memberInfo?.id == it){ //회원정보 삭제 성공
                    App.nowLogin = false
                    App.memberInfo = null
                    App.prefs.token = null
                    setResult(RESULT_OK)
                    //Toast.makeText(this,"회원정보 삭제 성공", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        )
    }

    fun showToastMsg(msg:String){ Toast.makeText(this,msg,Toast.LENGTH_SHORT).show() }
}