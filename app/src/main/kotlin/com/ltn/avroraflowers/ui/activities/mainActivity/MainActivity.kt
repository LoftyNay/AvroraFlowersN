package com.ltn.avroraflowers.ui.activities.mainActivity

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.adapters.ViewPagerAdapter
import com.ltn.avroraflowers.dagger.ActivityComponent
import com.ltn.avroraflowers.dagger.DaggerActivityComponent
import com.ltn.avroraflowers.dagger.module.FragmentManagerModule
import com.ltn.avroraflowers.ui.base.BaseActivity
import com.ltn.avroraflowers.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    companion object {
        lateinit var component: ActivityComponent
    }

    lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = buildDaggerComponent()
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        bottomNavigation.setOnNavigationItemSelectedListener(this)
        initViewPager()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bottom_navigation_main -> {
                viewPagerMain.setCurrentItem(ViewPagerAdapter.MAIN_FRAGMENT)
                item.setChecked(true)
                supportActionBar?.title = resources.getString(R.string.app_name)
            }
            R.id.bottom_navigation_cart -> {
                viewPagerMain.setCurrentItem(ViewPagerAdapter.CART_FRAGMENT)
                item.setChecked(true)
                supportActionBar?.title = resources.getString(R.string.cart_item_nav)
            }
            R.id.bottom_navigation_catalog -> {
                viewPagerMain.setCurrentItem(ViewPagerAdapter.CATALOG_FRAGMENT)
                item.setChecked(true)
                supportActionBar?.title = resources.getString(R.string.catalog_item_nav)
            }
            R.id.bottom_navigation_orders -> {
                viewPagerMain.setCurrentItem(ViewPagerAdapter.ORDERS_FRAGMENT)
                item.setChecked(true)
                supportActionBar?.title = resources.getString(R.string.orders_item_nav)
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

    private fun buildDaggerComponent(): ActivityComponent {
        return DaggerActivityComponent
            .builder()
            .fragmentManagerModule(FragmentManagerModule(supportFragmentManager))
            .build()
    }

    override fun onBackPressed() {
        val currentChildFragmentManager = viewPagerAdapter.getItem(viewPagerMain.currentItem).childFragmentManager
        if (currentChildFragmentManager.backStackEntryCount > 0) {
            currentChildFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
