package com.ltn.avroraflowers.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager


class Utils(var context: Context) {

    fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}