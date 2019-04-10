package com.ltn.avroraflowers.ui.activities.registerActivity.interactor

import com.google.android.material.textfield.TextInputEditText

interface IRegisterActivityInteractor {
    fun validateAndRegisterUser(
        name: TextInputEditText,
        email: TextInputEditText,
        password: TextInputEditText,
        onRegisterUser: OnRegisterUser
    )
}