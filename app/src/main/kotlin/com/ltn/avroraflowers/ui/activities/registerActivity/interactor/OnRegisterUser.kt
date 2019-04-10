package com.ltn.avroraflowers.ui.activities.registerActivity.interactor

interface OnRegisterUser {
    fun onUserRegistered()
    fun onUserExist()
    fun onValidateAndRegisterUserEnded()
    fun onFailure()
}