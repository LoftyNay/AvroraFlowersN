package com.ltn.avroraflowers.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ltn.avroraflowers.ui.fragments.cartFragment.HostCartFragment
import com.ltn.avroraflowers.ui.fragments.mainFragment.MainFragment
import com.ltn.avroraflowers.ui.fragments.catalogFragment.HostCatalogFragment
import com.ltn.avroraflowers.ui.fragments.ordersFragment.HostOrdersFragment


class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val fragmentsList: MutableList<Fragment> = ArrayList()

    companion object {
        val MAIN_FRAGMENT = 0
        val CART_FRAGMENT = 1
        val CATALOG_FRAGMENT = 2
        val ORDERS_FRAGMENT = 3
    }

    private val FRAGMENTS_COUNT = 4

    init {
        addFragment(MainFragment.getInstance())
        addFragment(HostCartFragment.getInstance())
        addFragment(HostCatalogFragment.getInstance())
        addFragment(HostOrdersFragment.getInstance())
    }

    override fun getItem(position: Int): Fragment {
        return fragmentsList[position]
    }

    override fun getCount(): Int {
        return FRAGMENTS_COUNT
    }

    fun addFragment(fragment: Fragment) {
        fragmentsList.add(fragment)
    }
}