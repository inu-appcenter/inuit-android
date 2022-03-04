package com.inu.appcenter.inuit.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.airbnb.lottie.LottieAnimationView

object Utility {

    fun istNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork : NetworkInfo? = cm.activeNetworkInfo
        val isConnected : Boolean = activeNetwork?.isConnectedOrConnecting == true

        return isConnected
    }

    fun focusEditText(context: Context,view: EditText){
        view.requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, 0)
    }

    fun outFocusEditText(context: Context,view: EditText){
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun startLoading(animation:LottieAnimationView){
        animation.visibility = View.VISIBLE
        animation.playAnimation()
    }

    fun pauseLoading(animation: LottieAnimationView){
        animation.visibility = View.GONE
        animation.pauseAnimation()
    }
}