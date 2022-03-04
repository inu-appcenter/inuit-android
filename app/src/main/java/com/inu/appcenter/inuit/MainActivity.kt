package com.inu.appcenter.inuit

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.inu.appcenter.inuit.login.App
import com.inu.appcenter.inuit.viewmodel.ClubListViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<ClubListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.setDataNoCategory()
        Log.d("MainActivity","viewModel.setDataNoCategory() 실행됨")

        val categoryButton : ImageButton = findViewById(R.id.imgBtn_category)
        categoryButton.setOnClickListener{
            startCategoryActivity()
        }

        val search : LinearLayout = findViewById(R.id.searchGroup)
        search.setOnClickListener {
            startSearchActivity()
        }

        val profileButton : ImageButton = findViewById(R.id.imgBtn_myprofile)
        profileButton.setOnClickListener {
            startActivityProperly()
        }

        val refreshLayout = findViewById<SwipeRefreshLayout>(R.id.refresh_layout_main)
        refreshLayout.setOnRefreshListener {
            viewModel.setDataNoCategory()
            refreshLayout.isRefreshing = false
        }
    }

    override fun onResume() {
        super.onResume()
        if(App.nowLogin){ //로그인이 되어 있는데 멤버 정보가 없다면 멤버 정보 불러오기.
            if(App.memberInfo == null){
                viewModel.requestMemberInfo(App.prefs.token!!)
                viewModel.memberInfo.observe(
                    this,
                    {
                        App.memberInfo = it
                    })
            }
        }
    }

    private fun startCategoryActivity(){
        val intent = CategoryActivity.newIntent(this@MainActivity)
        startActivity(intent)
    }

    private fun startSearchActivity(){
        val intent = SearchActivity.newIntent(this@MainActivity)
        startActivity(intent)
    }

    private fun startMyProfileActivity(){
        val intent = MyProfileActivity.newIntent(this@MainActivity)
        startActivity(intent)
    }

    private fun startLoginActivity(){
        val intent = LoginActivity.newIntent(this@MainActivity)
        startActivity(intent)
    }

    private fun startActivityProperly(){

        if(App.nowLogin && (App.memberInfo != null)){
            startMyProfileActivity()
        }else{
            startLoginActivity()
        }
    }
}