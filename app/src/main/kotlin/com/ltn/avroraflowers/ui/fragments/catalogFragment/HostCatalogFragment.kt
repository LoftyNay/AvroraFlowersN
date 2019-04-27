package com.ltn.avroraflowers.ui.fragments.catalogFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.ui.base.BaseFragment

class HostCatalogFragment : BaseFragment() {

    companion object {
        val TAG = "HostCatalogFragment"

        fun getInstance(): HostCatalogFragment {
            return HostCatalogFragment()
        }
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_catalog_host, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragment = CatalogFragment.getInstance()
        childFragmentManager.beginTransaction()
            .replace(R.id.fragmentCatalogContainer, fragment, CatalogFragment.TAG)
            .commit()
    }
}