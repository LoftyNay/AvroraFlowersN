package com.ltn.avroraflowers.ui.fragments.entryLoginFragment.interactor

interface OnCheckUserDataListener {
    fun onValidUserData()
    fun onEmailNotFound()
    fun onWrongPassword()
    fun onCheckEnded()
    fun onFailure()
}