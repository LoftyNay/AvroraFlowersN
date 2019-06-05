package com.ltn.avroraflowers.ui.fragments.ordersFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.ui.base.BaseFragment

class HostOrdersFragment : BaseFragment() {

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun showConnectionProblem() {
    }

    companion object {
        val TAG = "HostCatalogFragment"

        fun getInstance(): HostOrdersFragment {
            return HostOrdersFragment()
        }
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_orders_host, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragment = OrdersFragment.getInstance()
        childFragmentManager.beginTransaction()
            .replace(R.id.fragmentOrdersContainer, fragment, OrdersFragment.TAG)
            .commit()
    }
}
