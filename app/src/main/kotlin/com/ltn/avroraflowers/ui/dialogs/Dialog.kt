package com.ltn.avroraflowers.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window.FEATURE_NO_TITLE
import androidx.fragment.app.DialogFragment


class Dialog : DialogFragment() {

    companion object {
        private const val MESSAGE = "message"
        const val TAG = "dialog_tag"

        fun newInstance() = Dialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.ltn.avroraflowers.R.layout.dialog_loading, container)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): android.app.Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window.requestFeature(FEATURE_NO_TITLE)
        return dialog
    }
}