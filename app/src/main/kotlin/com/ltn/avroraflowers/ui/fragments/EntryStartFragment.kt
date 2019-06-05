package com.ltn.avroraflowers.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_entry_start.*

class EntryStartFragment : BaseFragment() {

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun showConnectionProblem() {
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_entry_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.toLoginFragment))
        registerButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.toRegisterActivity))
    }
}