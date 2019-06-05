package com.ltn.avroraflowers.ui.base

import com.arellomobile.mvp.MvpAppCompatActivity
import com.ltn.avroraflowers.ui.dialogs.Dialog

abstract class BaseActivity : MvpAppCompatActivity() {

    fun showLoadingDialog() {
        Dialog.newInstance().show(supportFragmentManager, Dialog.TAG)
    }

    fun closeDialogs() {
        val dialog = supportFragmentManager.findFragmentByTag(Dialog.TAG)
        if (dialog != null)
            (dialog as Dialog).dismiss()
    }
}