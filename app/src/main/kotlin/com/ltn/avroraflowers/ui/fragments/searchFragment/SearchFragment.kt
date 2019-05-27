package com.ltn.avroraflowers.ui.fragments.searchFragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.ui.base.BaseFragment

class SearchFragment: DialogFragment() {

    companion object {
        val TAG = "SearchFragment"

        fun getInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container)
    }

}