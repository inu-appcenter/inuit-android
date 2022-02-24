package com.inu.appcenter.inuit.login

import android.app.Application

class App : Application(){

    companion object{
        lateinit var prefs: Preference
        var nowLogin :Boolean = false
    }

    override fun onCreate() {
        prefs = Preference(applicationContext)
        val autoLogin = prefs.autoLogin
        val token = prefs.token
        if(autoLogin == true){
            if(token != null){
                nowLogin = true
            }
        }
        super.onCreate()
    }
}