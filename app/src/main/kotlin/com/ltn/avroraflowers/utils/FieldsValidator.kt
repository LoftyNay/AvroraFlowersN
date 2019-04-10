package com.ltn.avroraflowers.utils

import android.content.Context
import android.util.Patterns
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.ltn.avroraflowers.R
import java.util.regex.Pattern

class FieldsValidator(context: Context) {

    private val EMAIL_VALIDATION_MSG = context.getString(R.string.email_validation_msg)
    private val NAME_VALIDATION_MSG = context.getString(R.string.name_validation_msg)
    private val PASSWORD_VALIDATION_MSG = context.getString(R.string.password_validation_msg)

    fun isNameValid(textInputEditText: TextInputEditText): Boolean {
        val textInEdit = textInputEditText.text?.trim().toString()
        val valid = textInEdit.length >= 2

        setErrorMessage(textInputEditText, valid, NAME_VALIDATION_MSG)

        return valid
    }

    fun isEmailValid(textInputEditText: TextInputEditText): Boolean {
        val strInEdit = textInputEditText.text?.trim().toString()
        val valid = Patterns.EMAIL_ADDRESS.matcher(strInEdit).matches()

        setErrorMessage(textInputEditText, valid, EMAIL_VALIDATION_MSG)

        return valid
    }

    fun isPasswordValid(textInputEditText: TextInputEditText): Boolean {
        val textInEdit = textInputEditText.text?.trim().toString()
        val valid = textInEdit.length >= 5

        setErrorMessage(textInputEditText, valid, PASSWORD_VALIDATION_MSG)

        return valid
    }

    private fun setErrorMessage(textInputEditText: TextInputEditText, valid: Boolean, message: String) {
        val textInputLayout = textInputEditText.parent.parent as TextInputLayout
        if (valid) {
            textInputLayout.error = null
        } else {
            textInputLayout.error = message
            textInputLayout.requestFocus()
        }
    }
}