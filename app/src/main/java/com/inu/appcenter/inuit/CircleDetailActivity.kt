package com.inu.appcenter.inuit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import com.inu.appcenter.inuit.viewmodel.CircleDetailViewModel

class CircleDetailActivity : AppCompatActivity() {

    private val viewModel by viewModels<CircleDetailViewModel>()

    lateinit var circleNameTitle : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle_detail)

        circleNameTitle = findViewById(R.id.tv_top_title_circle_name)
        circleNameTitle.text = intent.getStringExtra("CIRCLE_NAME")

        val circleId = intent.getIntExtra("CIRCLE_ID",0)
        viewModel.requestCircleDetail(circleId)
        Log.d("requestCircleDetail실행됨", "$circleId")
        viewModel.circleContent.observe(
            this,{

            }
        )

        val backButton = findViewById<ImageButton>(R.id.btn_back)
        backButton.setOnClickListener {
            finish()
        }
    }

    companion object{
        fun newIntent(context: Context, circleId : Int, circleName: String): Intent {
            return Intent(context, CircleDetailActivity::class.java).apply {
                putExtra("CIRCLE_ID",circleId)
                putExtra("CIRCLE_NAME",circleName)
            }
        }
    }
}