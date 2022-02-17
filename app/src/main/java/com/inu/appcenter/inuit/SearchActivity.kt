package com.inu.appcenter.inuit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import com.inu.appcenter.inuit.fragment.SearchInfoFragment

class SearchActivity : AppCompatActivity() {

    lateinit var et_search : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val backButton = findViewById<ImageButton>(R.id.btn_back)
        backButton.setOnClickListener {
            finish()
        }

        et_search = findViewById(R.id.et_search)
        focusEditText(et_search)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.search_fragment_container)
        if (currentFragment == null){
            val fragment = SearchInfoFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.search_fragment_container,fragment)
                .commit()
        }
    }

    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }

    private fun focusEditText(view:EditText){
        view.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, 0)
    }
}