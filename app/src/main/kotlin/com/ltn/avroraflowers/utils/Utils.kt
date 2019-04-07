package com.ltn.avroraflowers.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.getSystemService


class Utils {
    companion object {
        fun showSoftKeyboard(view: View, context: Context) {
            if (view.requestFocus()) {
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm!!.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }
}