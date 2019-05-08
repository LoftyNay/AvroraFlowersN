package com.ltn.avroraflowers.ui.activities.mainActivity

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.adapters.ViewPagerAdapter
import com.ltn.avroraflowers.ui.base.BaseActivity
import com.ltn.avroraflowers.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigation.setOnNavigationItemSelectedListener(this)
        initViewPager()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bottom_navigation_main -> {
                viewPagerMain.setCurrentItem(ViewPagerAdapter.MAIN_FRAGMENT)
                item.setChecked(true)
            }
            R.id.bottom_navigation_cart -> {
                viewPagerMain.setCurrentItem(ViewPagerAdapter.CART_FRAGMENT)
                item.setChecked(true)
            }
            R.id.bottom_navigation_catalog -> {
                viewPagerMain.setCurrentItem(ViewPagerAdapter.CATALOG_FRAGMENT)
                item.setChecked(true)
            }
            R.id.bottom_navigation_orders -> {
                viewPagerMain.setCurrentItem(ViewPagerAdapter.ORDERS_FRAGMENT)
                item.setChecked(true)
            }
        }
        return false
    }

    private fun initViewPager() {
        viewPagerMain.setEnablePaging(false)
        viewPagerMain.setEnableSmoothScroll(false)
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerMain.offscreenPageLimit = Constants.PAGE_LIMIT
        viewPagerMain.adapter = viewPagerAdapter
    }

    override fun onBackPressed() {
        val currentChildFragmentManager = viewPagerAdapter.getItem(viewPagerMain.currentItem).childFragmentManager
        if (currentChildFragmentManager.backStackEntryCount != 0) {
            currentChildFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
