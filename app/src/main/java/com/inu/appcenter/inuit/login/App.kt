package com.inu.appcenter.inuit.login

import android.app.Application
import com.inu.appcenter.inuit.retrofit.dto.MemberInfo

class App : Application(){

    companion object{
        lateinit var prefs: Preference
        var nowLogin :Boolean = false
        var memberInfo : MemberInfo? = null
    }

    override fun onCreate() {
        prefs = Preference(applicationContext)
        val autoLogin = prefs.autoLogin
        if(autoLogin == true){
            val token = prefs.token
            if(token != null){
                nowLogin = true
            }
        }
        super.onCreate()
    }
}