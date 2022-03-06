package com.inu.appcenter.inuit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inu.appcenter.inuit.login.App
import com.inu.appcenter.inuit.recycler.MyClubListAdapter
import com.inu.appcenter.inuit.recycler.OnMyCircleClick
import com.inu.appcenter.inuit.viewmodel.MyProfileViewModel

class MyProfileActivity : AppCompatActivity(),OnMyCircleClick {

    private val viewModel by viewModels<MyProfileViewModel>()
    private lateinit var editResult : ActivityResultLauncher<Intent>
    private lateinit var addResult : ActivityResultLauncher <Intent>
    private lateinit var adapter : MyClubListAdapter


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
        adapter = MyClubListAdapter(this)
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
                updateMemberInfo()
            }
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MyProfileActivity::class.java)
        }
    }

    override fun onMyCircleDeleteClick(id: Int) {
       deleteCircle(id)
    }

    override fun onMyCircleEditClick(id: Int) {
        //뷰모델에서 동아리 수정하기 실행
        val intent = PostCircleActivity.newPatchIntent(this,true,id)
        startActivity(intent)
    }

    override fun onMyCircleDescClick(id: Int) {
        //동아리 상세페이지로 이동.
    }

    override fun showDeleteDialog(id:Int) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.delete_circle_dialog_title))
            .setMessage(getString(R.string.delete_circle_dialog_msg))
            .setPositiveButton("확인") { dialog, which ->
                deleteCircle(id)
                dialog.dismiss()
                Log.d("MyTag", "positive") }
            .setNegativeButton("취소") { dialog, which ->
                dialog.dismiss()
                Log.d("MyTag", "negative") }
            .create()
            .show()
    }

    fun updateMemberInfo(){
        viewModel.requestMemberInfo(App.prefs.token!!)
        viewModel.memberInfo.observe(
            this,
            {
                App.memberInfo = it
                if(it.circleId != null){
                    adapter.setMyClub(it.circleId!!,it.circleName!!)
                }else{
                    adapter.clearAll()
                }
            }
        )
    }

    fun deleteCircle(id:Int){
        viewModel.deleteCircle(App.prefs.token!!,id)
        viewModel.deletedCircleId.observe(
            this,
            {
                if(it == id){
                    Toast.makeText(this,"동아리 삭제 성공",Toast.LENGTH_SHORT).show()
                    updateMemberInfo()
                }
            }
        )
    }
}