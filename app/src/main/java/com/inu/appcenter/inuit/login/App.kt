package com.inu.appcenter.inuit.login

import android.app.Application

class App : Application(){
    companion object{
        lateinit var prefs: Preference
    }
    override fun onCreate() {
        prefs = Preference(applicationContext)
        super.onCreate()
    }
}