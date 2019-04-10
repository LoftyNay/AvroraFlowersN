package com.ltn.avroraflowers.utils

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputLayout
import com.ltn.avroraflowers.utils.FieldsValidator

class TextWatch(var textInputLayout: TextInputLayout) : TextWatcher {

    override fun afterTextChanged(s: Editable?) {
        textInputLayout.error = null
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}