package com.inu.appcenter.inuit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inu.appcenter.inuit.login.App
import com.inu.appcenter.inuit.recycler.MyClubListAdapter
import com.inu.appcenter.inuit.viewmodel.MyProfileViewModel

class MyProfileActivity : AppCompatActivity() {

    private val viewModel by viewModels<MyProfileViewModel>()
    private lateinit var editResult : ActivityResultLauncher<Intent>
    private lateinit var addResult : ActivityResultLauncher <Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        val memberInfo = App.memberInfo

        val userNickName = findViewById<TextView>(R.id.tv_user_nickname)
        val userEmail = findViewById<TextView>(R.id.tv_user_email)
        userNickName.text = memberInfo?.nickname
        userEmail.text = memberInfo?.email

        val backButton = findViewById<ImageButton>(R.id.btn_back)
        backButton.setOnClickListener {
            finish()
        }

        editResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                finish()
            }
        }

        val editMyProfile = findViewById<TextView>(R.id.change_my_profile)
        editMyProfile.setOnClickListener {
            val intent = EditProfileActivity.newIntent(this@MyProfileActivity)
            editResult.launch(intent)
        }

        val recycler_myclub_List = findViewById<RecyclerView>(R.id.recycler_myclub_list)
        recycler_myclub_List.layoutManager = LinearLayoutManager(this)
        val adapter = MyClubListAdapter()
        recycler_myclub_List.adapter = adapter
        if(memberInfo?.circleId != null && memberInfo?.circleName!= null){
            adapter.setMyClub(memberInfo.circleId,memberInfo.circleName)
        }

        val addNewClub = findViewById<TextView>(R.id.tv_add_new_club)
        addNewClub.setOnClickListener {
            val intent = PostCircleActivity.newIntent(this@MyProfileActivity)
            addResult.launch(intent)
        }

        addResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                viewModel.requestMemberInfo(App.prefs.token!!)
                viewModel.memberInfo.observe(
                    this,
                    {
                        adapter.setMyClub(it.circleId!!,it.circleName!!)
                    }
                )
            }
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MyProfileActivity::class.java)
        }
    }
}