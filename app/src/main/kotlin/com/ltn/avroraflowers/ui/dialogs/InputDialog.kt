package com.ltn.avroraflowers.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.textfield.TextInputEditText
import com.ltn.avroraflowers.App
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.utils.Utils
import javax.inject.Inject

class InputDialog : DialogFragment() {

    @Inject
    lateinit var utils: Utils

    companion object {
        const val TAG = "dialog_input_tag"
        const val MESSAGE = "dialog_input_message"

        fun newInstance(): InputDialog {
            val fragment = InputDialog()
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component.inject(this)
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_input, null)
        val et = view.findViewById<TextInputEditText>(R.id.etDialogInput)
        val builder = AlertDialog.Builder(activity as Context)
            .setTitle(getString(R.string.name_order))
            .setMessage(getString(R.string.save_cart_message))
            .setView(view)
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                val onEditTextConfirmListener = targetFragment as OnEditTextConfirmListener
                val name = et.text.toString().trim()
                onEditTextConfirmListener.onConfirm(name)
            }
            .setNegativeButton(getString(R.string.cancel), { dialog, which -> })
        val dialog = builder.create()
        dialog.setOnShowListener { dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false }
        dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = isValid(s.toString().trim())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        et.requestFocus()
        return dialog
    }

    private fun isValid(name: String): Boolean {
        return name.isNotEmpty()
    }

    interface OnEditTextConfirmListener {
        fun onConfirm(name: String)
    }
}
