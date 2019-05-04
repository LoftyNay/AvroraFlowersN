package com.ltn.avroraflowers.ui.fragments.innerProductFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_catalog_products_inner.*
import kotlinx.android.synthetic.main.toolbar.*

class InnerProductFragment : BaseFragment() {

    companion object {
        fun getInstance(): InnerProductFragment {
            return InnerProductFragment()
        }
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_catalog_products_inner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (toolbarInnerProd as Toolbar).title = "inner"
    }
}