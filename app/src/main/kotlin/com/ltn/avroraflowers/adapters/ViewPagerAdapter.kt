package com.ltn.avroraflowers.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ltn.avroraflowers.ui.fragments.CartFragment
import com.ltn.avroraflowers.ui.fragments.MainFragment
import com.ltn.avroraflowers.ui.fragments.OrdersFragment
import com.ltn.avroraflowers.ui.fragments.catalogFragment.CatalogFragment
import android.R



class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val fragmentsList: MutableList<Fragment> = ArrayList()

    companion object {
        val MAIN_FRAGMENT = 0
        val CART_FRAGMENT = 1
        val CATALOG_FRAGMENT = 2
        val ORDERS_FRAGMENT = 3
    }

    private val FRAGMENTS_COUNT = 4

    override fun getItem(position: Int): Fragment {
        return fragmentsList.get(position)
    }

    override fun getCount(): Int {
        return FRAGMENTS_COUNT
    }

    fun addFragment(fragment: Fragment) {
        fragmentsList.add(fragment)
    }
}