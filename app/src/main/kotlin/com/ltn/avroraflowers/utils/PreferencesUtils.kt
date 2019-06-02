package com.ltn.avroraflowers.utils

import android.content.SharedPreferences
import com.ltn.avroraflowers.network.Response.LoginResponse

class PreferencesUtils(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val SHARED_PREFERENCES = "PreferencesAvrora"
        const val NAME = "NAME"
        const val TOKEN = "TOKEN"
    }

    private val DEF_VALUE = "def"

    fun putUserData(loginResponse: LoginResponse) {
        sharedPreferences.edit()
            .putString(NAME, loginResponse.name)
            .putString(TOKEN, loginResponse.token)
            .apply()
    }

    fun clearUserData() {
        sharedPreferences.edit()
            .remove(NAME)
            .remove(TOKEN)
            .apply()
    }

    fun registerChangePreferences(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterChangePreferences(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    fun getToken(): String? {
        val token = sharedPreferences.getString(TOKEN, DEF_VALUE)
        if (token != null) {
            return token
        }
        return null
    }

    fun getName(): String? {
        val name = sharedPreferences.getString(NAME, DEF_VALUE)
        if (name != null) {
            return name
        }
        return null
    }


    fun isLogin():Boolean {
        return sharedPreferences.getString(TOKEN, DEF_VALUE) != DEF_VALUE
    }
}