package com.ltn.avroraflowers.utils

import android.content.Context
import android.util.Patterns
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class FieldsValidator {

    fun isNameValid(textInputEditText: TextInputEditText): Boolean {
        val textInEdit = textInputEditText.text?.trim().toString()
        return textInEdit.length >= 2
    }

    fun isEmailValid(textInputEditText: TextInputEditText): Boolean {
        val strInEdit = textInputEditText.text?.trim().toString()
        return Patterns.EMAIL_ADDRESS.matcher(strInEdit).matches()
    }

    fun isPasswordValid(textInputEditText: TextInputEditText): Boolean {
        val textInEdit = textInputEditText.text?.trim().toString()
        return textInEdit.length >= 5
    }
}