package com.ltn.avroraflowers.ui.fragments.searchFragment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ltn.avroraflowers.R


class HostSearch : DialogFragment() {

    companion object {
        fun getInstance() = HostSearch()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_search_host, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogFragment)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(activity!!, theme) {
            override fun onBackPressed() {
                if (childFragmentManager.backStackEntryCount != 0) {
                    childFragmentManager.popBackStack()
                    Log.d("GLL","DSD")
                } else
                    dismiss()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val fragment = SearchFragment.getInstance()
        childFragmentManager.beginTransaction()
            .replace(R.id.searchContainer, fragment, SearchFragment.TAG)
            .commit()
    }
}