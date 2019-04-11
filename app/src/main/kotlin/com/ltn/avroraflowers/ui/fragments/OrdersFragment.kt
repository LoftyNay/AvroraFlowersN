package com.ltn.avroraflowers.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.ui.base.BaseFragment

class OrdersFragment : BaseFragment() {

    companion object {
        fun newInstance(): OrdersFragment {
            return OrdersFragment()
        }
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }
}