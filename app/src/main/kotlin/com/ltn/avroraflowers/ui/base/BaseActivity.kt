package com.ltn.avroraflowers.ui.base

import com.arellomobile.mvp.MvpAppCompatActivity
import com.ltn.avroraflowers.ui.dialogs.LoadingDialog

abstract class BaseActivity : MvpAppCompatActivity() {

    fun showLoadingDialog() {
        val dialog = supportFragmentManager.findFragmentByTag(LoadingDialog.TAG)
        if (dialog == null) {
            LoadingDialog.newInstance(false).show(supportFragmentManager, LoadingDialog.TAG)
        }
    }

    fun closeDialogs() {
        val dialog = supportFragmentManager.findFragmentByTag(LoadingDialog.TAG)
        if (dialog != null)
            (dialog as LoadingDialog).dismiss()
    }
}