package com.ltn.avroraflowers.ui.activities.registerActivity.interactor

import com.google.android.material.textfield.TextInputEditText

interface IRegisterActivityInteractor {
    fun registerUser(
        email: String,
        password: String,
        onRegisterUserListener: OnRegisterUserListener
    )
}