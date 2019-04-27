package com.ltn.avroraflowers.ui.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import com.arellomobile.mvp.MvpAppCompatFragment
import com.ltn.avroraflowers.App
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.ui.activities.mainActivity.MainActivity
import com.ltn.avroraflowers.ui.fragments.productsFragment.ProductsFragment
import com.ltn.avroraflowers.utils.FragmentManagerUtils
import javax.inject.Inject


abstract class BaseFragment : MvpAppCompatFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflateView(inflater, container, savedInstanceState)
    }

    abstract fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
}