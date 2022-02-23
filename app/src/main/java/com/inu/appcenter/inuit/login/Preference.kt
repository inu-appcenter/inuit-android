package com.inu.appcenter.inuit.login

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Preference(context: Context) {
    private val loginPrefName = "LOGIN_PREFERENCE"
    private val loginPreference = context.getSharedPreferences(loginPrefName,MODE_PRIVATE)

    var token:String?
        get() = loginPreference.getString("token",null)
        set(value){
            loginPreference.edit().putString("token",value).apply()
        }
    var autoLogin:Boolean?
        get() = loginPreference.getBoolean("autoLogin",false)
        set(value) {
            loginPreference.edit().putBoolean("autoLogin",value!!).apply()
        }
}