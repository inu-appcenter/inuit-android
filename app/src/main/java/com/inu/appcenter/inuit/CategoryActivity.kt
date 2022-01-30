package com.inu.appcenter.inuit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        val backButton = findViewById<ImageButton>(R.id.btn_back)
        backButton.setOnClickListener {
            finish()
        }

        //모든 버튼 찾아서 리스너 달아주기
        val btn_academic = findViewById<Button>(R.id.btn_academic)
        val btn_culture = findViewById<Button>(R.id.btn_culture)
        val btn_exercise = findViewById<Button>(R.id.btn_exercise)
        val btn_hobby_exhibition = findViewById<Button>(R.id.btn_hobby_exhibition)
        val btn_service = findViewById<Button>(R.id.btn_service)
        val btn_religion = findViewById<Button>(R.id.btn_religion)
        val btn_etc = findViewById<Button>(R.id.btn_etc)

        btn_academic.setOnClickListener(CategorybtnListner())
        btn_culture.setOnClickListener(CategorybtnListner())
        btn_exercise.setOnClickListener(CategorybtnListner())
        btn_hobby_exhibition.setOnClickListener(CategorybtnListner())
        btn_service.setOnClickListener(CategorybtnListner())
        btn_religion.setOnClickListener(CategorybtnListner())
        btn_etc.setOnClickListener(CategorybtnListner())
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CategoryActivity::class.java)
        }
    }

    inner class CategorybtnListner : View.OnClickListener{

        lateinit var category: String

        override fun onClick(v: View?) {

            when(v?.id){
                R.id.btn_academic -> { category = getString(R.string.academic_title) }

                R.id.btn_culture -> { category = getString(R.string.culture_title) }

                R.id.btn_exercise -> { category = getString(R.string.exercise_title) }

                R.id.btn_hobby_exhibition -> { category = getString(R.string.hobby_exhibition_title) }

                R.id.btn_service -> { category = getString(R.string.service_title) }

                R.id.btn_religion -> { category = getString(R.string.religion_title) }

                R.id.btn_etc -> { category = getString(R.string.etc_title) }
            }
            val intent = SelectedCategoryActivity.newIntent(this@CategoryActivity,category)
            startActivity(intent)
            finish()
        }
    }

}