package com.inu.appcenter.inuit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.inu.appcenter.inuit.fragment.SearchInfoFragment
import com.inu.appcenter.inuit.fragment.SearchListFragment
import com.inu.appcenter.inuit.util.Utility
import com.inu.appcenter.inuit.viewmodel.SearchViewModel

class SearchActivity : AppCompatActivity() {

    private val viewModel by viewModels<SearchViewModel>()

    lateinit var et_search : EditText
    lateinit var searchButton : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val backButton = findViewById<ImageButton>(R.id.btn_back)
        backButton.setOnClickListener {
            finish()
        }
        searchButton = findViewById(R.id.iv_search)
        searchButton.setOnClickListener {
            if(et_search.text.isNotEmpty()){
                Utility.outFocusEditText(this,et_search)
                search()
            }
        }

        et_search = findViewById(R.id.et_search)
        Utility.focusEditText(this,et_search)
        et_search.setOnEditorActionListener { v, id, event ->
            if(id == EditorInfo.IME_ACTION_SEARCH){
                if(et_search.text.isNotEmpty()){
                    Utility.outFocusEditText(this,et_search)
                    search()
                }
            }
            true
        }

        val currentFragment = supportFragmentManager.findFragmentById(R.id.search_fragment_container)
        if (currentFragment == null){ setFragment(SearchInfoFragment(getString(R.string.search_info_text))) }
    }

    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }

    private fun search(){
        setFragment(SearchListFragment())
        val keyword = et_search.text.toString()
        viewModel.search(keyword) //뷰모델의 검색 메서드 실행.
        viewModel.searchResultClubList.observe(this,
            {
                if(it.isEmpty()) {
                    setFragment(SearchInfoFragment(getString(R.string.search_no_result)))
                }
            })
    }

    private fun setFragment(fragment:Fragment){
        supportFragmentManager
            .beginTransaction()
            .add(R.id.search_fragment_container,fragment)
            .commit()
    }
}