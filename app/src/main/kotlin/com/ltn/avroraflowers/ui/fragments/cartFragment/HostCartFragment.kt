package com.ltn.avroraflowers.ui.fragments.cartFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.ui.base.BaseFragment
import com.ltn.avroraflowers.ui.fragments.catalogFragment.CatalogFragment


class HostCartFragment : BaseFragment() {

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun showConnectionProblem() {
    }

    companion object {
        fun getInstance(): HostCartFragment {
            return HostCartFragment()
        }
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_cart_host, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragment = CartFragment.getInstance()
        childFragmentManager.beginTransaction()
            .replace(R.id.cartFragmentContainer, fragment, CatalogFragment.TAG)
            .commit()
    }
}