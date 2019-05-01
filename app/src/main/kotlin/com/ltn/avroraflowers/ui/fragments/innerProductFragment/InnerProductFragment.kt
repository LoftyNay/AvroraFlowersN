package com.ltn.avroraflowers.ui.fragments.innerProductFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.ui.base.BaseFragment

class InnerProductFragment : BaseFragment() {

    companion object {
        fun getInstance(): InnerProductFragment {
            return InnerProductFragment()
        }
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_catalog_products_inner, container, false)
    }
}