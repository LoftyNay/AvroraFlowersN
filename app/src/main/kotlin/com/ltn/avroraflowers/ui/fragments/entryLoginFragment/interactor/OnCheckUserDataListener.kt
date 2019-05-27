package com.ltn.avroraflowers.ui.fragments.entryLoginFragment.interactor

import com.ltn.avroraflowers.network.Response.LoginResponse

interface OnCheckUserDataListener {
    fun onValidUserData(profile: LoginResponse)
    fun onEmailNotFound()
    fun onWrongPassword()
    fun onCheckEnded()
    fun onFailure()
}