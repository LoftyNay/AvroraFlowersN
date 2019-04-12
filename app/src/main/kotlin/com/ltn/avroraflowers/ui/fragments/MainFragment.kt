package com.ltn.avroraflowers.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatActivity
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.ui.base.BaseFragment

class MainFragment : BaseFragment() {

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }
}