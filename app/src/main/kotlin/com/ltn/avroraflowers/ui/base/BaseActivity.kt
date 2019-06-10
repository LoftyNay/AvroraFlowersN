package com.ltn.avroraflowers.ui.base

import androidx.appcompat.app.AlertDialog
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

    fun showDialog(
        title: String,
        message: String,
        positiveTitle: String,
        negativeTitle: String,
        listener: BaseFragment.DialogListener
    ) {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveTitle) { dialog, which ->
                listener.onPositive()
                dialog.dismiss()
            }
            .setNegativeButton(negativeTitle) { dialog, which ->
                listener.onNegative()
                dialog.dismiss()
            }
            .create()
        alertDialog.show()
    }
}