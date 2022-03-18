package com.inu.appcenter.inuit.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.airbnb.lottie.LottieAnimationView
import com.inu.appcenter.inuit.R

class LoadingDialog(context: Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_dialog)

        val animation = findViewById<LottieAnimationView>(R.id.loading_animation)
        Utility.startLoading(animation)

        setCancelable(false)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}