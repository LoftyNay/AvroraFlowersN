package com.ltn.avroraflowers.ui.activities.mainActivity

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.adapters.ViewPagerAdapter
import com.ltn.avroraflowers.ui.base.BaseActivity
import com.ltn.avroraflowers.ui.fragments.CartFragment
import com.ltn.avroraflowers.ui.fragments.MainFragment
import com.ltn.avroraflowers.ui.fragments.OrdersFragment
import com.ltn.avroraflowers.ui.fragments.catalogFragment.CatalogFragment
import com.ltn.avroraflowers.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.hide()

        bottomNavigation.setOnNavigationItemSelectedListener(this)

        initViewPager()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bottom_navigation_main -> {
                viewPagerMain.setCurrentItem(ViewPagerAdapter.MAIN_FRAGMENT)
                item.setChecked(true)
                supportActionBar?.hide()
            }
            R.id.bottom_navigation_cart -> {
                viewPagerMain.setCurrentItem(ViewPagerAdapter.CART_FRAGMENT)
                item.setChecked(true)
                supportActionBar?.show()
                supportActionBar?.title = resources.getString(R.string.cart_item_nav)
            }
            R.id.bottom_navigation_catalog -> {
                viewPagerMain.setCurrentItem(ViewPagerAdapter.CATALOG_FRAGMENT)
                item.setChecked(true)
                supportActionBar?.show()
                supportActionBar?.title = resources.getString(R.string.catalog_item_nav)
            }
            R.id.bottom_navigation_orders -> {
                viewPagerMain.setCurrentItem(ViewPagerAdapter.ORDERS_FRAGMENT)
                item.setChecked(true)
                supportActionBar?.show()
                supportActionBar?.title = resources.getString(R.string.orders_item_nav)
            }
        }
        return false
    }

    private fun initViewPager() {
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.se
        viewPagerMain.offscreenPageLimit = Constants.PAGE_LIMIT

        viewPagerAdapter.addFragment(MainFragment.newInstance())
        viewPagerAdapter.addFragment(CartFragment.newInstance())
        viewPagerAdapter.addFragment(CatalogFragment.newInstance())
        viewPagerAdapter.addFragment(OrdersFragment.newInstance())
        viewPagerMain.adapter = viewPagerAdapter
    }
}
