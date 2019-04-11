package com.ltn.avroraflowers.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import com.arellomobile.mvp.MvpAppCompatFragment

abstract class BaseFragment : MvpAppCompatFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflateView(inflater, container, savedInstanceState)
    }

    abstract fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
}