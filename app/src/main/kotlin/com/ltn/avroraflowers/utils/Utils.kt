package com.ltn.avroraflowers.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.button.MaterialButton
import com.ltn.avroraflowers.R


class Utils(var context: Context) {

    fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun hideSoftKeyboard(view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(view.windowToken, 0)
    }

    //FOR PROGRESS BAR IN BUTTON
    fun viewToFront(view: View) {
        view.z = 99f
        view.invalidate()
    }

    fun showTextAndEnableButton(button: MaterialButton, text: String) {
        button.isEnabled = true
        button.text = text
    }

    fun hideTextAndDisableButton(button: MaterialButton) {
        button.isEnabled = false
        button.text = null
    }
}