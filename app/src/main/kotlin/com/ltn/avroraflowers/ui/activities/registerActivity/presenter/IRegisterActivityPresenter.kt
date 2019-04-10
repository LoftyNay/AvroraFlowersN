package com.ltn.avroraflowers.ui.activities.registerActivity.presenter

import com.google.android.material.textfield.TextInputEditText

interface IRegisterActivityPresenter {
    fun toRegisterUser(
        nameEditText: TextInputEditText,
        emailEditText: TextInputEditText,
        passwordEditText: TextInputEditText
    )
}