package com.ltn.avroraflowers.ui.fragments.entryLoginFragment.interactor

interface OnCheckUserDataFromServer {
    fun onValidUserData()
    fun onEmailNotFound()
    fun onWrongPassword()
}