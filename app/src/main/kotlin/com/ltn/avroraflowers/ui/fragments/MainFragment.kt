package com.ltn.avroraflowers.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.ui.base.BaseFragment
import kotlinx.android.synthetic.main.toolbar_with_search.*

class MainFragment : BaseFragment() {

    companion object {
        fun getInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //  (activity as MvpAppCompatActivity).setSupportActionBar(toolbar)
        //  (activity as MvpAppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbarSearch.title = resources.getString(R.string.app_name)
    }
}