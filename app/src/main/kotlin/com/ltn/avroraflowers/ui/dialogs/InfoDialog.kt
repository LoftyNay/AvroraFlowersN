package com.ltn.avroraflowers.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import com.ltn.avroraflowers.R
import kotlinx.android.synthetic.main.toolbar.*

class InfoDialog : DialogFragment() {

    enum class Type {
        ABOUT,
        CONTACTS
    }

    companion object {
        const val TAG = "dialog_about_tag"
        const val TITLE = "dialog_about_title"
        const val DIALOG_TYPE = "dialog_about_type"

        fun newInstanceAbout(title: String): InfoDialog {
            val bundle = Bundle()
            val fragment = InfoDialog()
            bundle.putString(TITLE, title)
            bundle.putString(DIALOG_TYPE, Type.ABOUT.name)
            fragment.arguments = bundle
            return fragment
        }

        fun newInstanceContacts(title: String): InfoDialog {
            val bundle = Bundle()
            val fragment = InfoDialog()
            bundle.putString(TITLE, title)
            bundle.putString(DIALOG_TYPE, Type.CONTACTS.name)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogFragment)
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    private fun setNavigationIcon(toolbar: View, title: String) {
        (toolbar as Toolbar).title = title
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow)
        toolbar.setNavigationOnClickListener {
            dismiss()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            setNavigationIcon(toolbar, it.getString(TITLE)!!)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        arguments?.let {
            when (arguments!!.getString(DIALOG_TYPE)) {
                Type.ABOUT.name -> {
                    return inflater.inflate(R.layout.layout_about, container, false)
                }
                Type.CONTACTS.name -> {
                    return inflater.inflate(R.layout.layout_contacts, container, false)
                }
                else -> return super.onCreateView(inflater, container, savedInstanceState)
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}