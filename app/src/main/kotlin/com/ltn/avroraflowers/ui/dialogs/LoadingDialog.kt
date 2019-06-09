package com.ltn.avroraflowers.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window.FEATURE_NO_TITLE
import androidx.fragment.app.DialogFragment
import com.ltn.avroraflowers.R


class LoadingDialog : DialogFragment() {

    companion object {
        private const val CANCELABLE = "cancelable"
        const val TAG = "dialog_loading_tag"

        fun newInstance(isCancelable: Boolean): LoadingDialog {
            val bundle = Bundle()
            val fragment = LoadingDialog()
            bundle.putBoolean(CANCELABLE, isCancelable)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isCancelable = savedInstanceState?.getBoolean(CANCELABLE)
        if (isCancelable != null) {
            this.isCancelable = isCancelable
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_loading, container)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): android.app.Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window.requestFeature(FEATURE_NO_TITLE)
        return dialog
    }
}