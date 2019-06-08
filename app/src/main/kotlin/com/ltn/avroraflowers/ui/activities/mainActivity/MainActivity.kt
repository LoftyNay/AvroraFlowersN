package com.ltn.avroraflowers.ui.activities.mainActivity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.fragment.app.FragmentManager
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
                viewPagerMain.currentItem = ViewPagerAdapter.MAIN_FRAGMENT
                if (item.isChecked) toFirstFragmentInStack(ViewPagerAdapter.MAIN_FRAGMENT)
                item.isChecked = true
            }
            R.id.bottom_navigation_cart -> {
                viewPagerMain.currentItem = ViewPagerAdapter.CART_FRAGMENT
                if (item.isChecked) toFirstFragmentInStack(ViewPagerAdapter.CART_FRAGMENT)
                item.isChecked = true
            }
            R.id.bottom_navigation_catalog -> {
                viewPagerMain.currentItem = ViewPagerAdapter.CATALOG_FRAGMENT
                if (item.isChecked) toFirstFragmentInStack(ViewPagerAdapter.CATALOG_FRAGMENT)
                item.isChecked = true
            }
            R.id.bottom_navigation_orders -> {
                viewPagerMain.currentItem = ViewPagerAdapter.ORDERS_FRAGMENT
                if (item.isChecked) toFirstFragmentInStack(ViewPagerAdapter.ORDERS_FRAGMENT)
                item.isChecked = true
            }
        }
        return false
    }

    fun setPagerItem(item: Int) {
        viewPagerMain.currentItem = item
        bottomNavigation.menu[item].isChecked = true
    }

    private fun initViewPager() {
        viewPagerMain.setEnablePaging(false)
        viewPagerMain.setEnableSmoothScroll(false)
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerMain.offscreenPageLimit = Constants.PAGE_LIMIT
        viewPagerMain.adapter = viewPagerAdapter
    }

    fun clearBackStack() {
        clearStack(viewPagerAdapter.getItem(ViewPagerAdapter.MAIN_FRAGMENT).childFragmentManager)
        clearStack(viewPagerAdapter.getItem(ViewPagerAdapter.CART_FRAGMENT).childFragmentManager)
        clearStack(viewPagerAdapter.getItem(ViewPagerAdapter.CATALOG_FRAGMENT).childFragmentManager)
        clearStack(viewPagerAdapter.getItem(ViewPagerAdapter.ORDERS_FRAGMENT).childFragmentManager)
    }

    private fun clearStack(fm: FragmentManager) {
        for (i in 0 until fm.backStackEntryCount) {
            fm.popBackStack()
        }
    }

    private fun toFirstFragmentInStack(item: Int) {
        val currentItem = viewPagerAdapter.getItem(item)
        if (currentItem.childFragmentManager.backStackEntryCount != 0) {
            clearStack(currentItem.childFragmentManager)
        }
    }

    override fun onBackPressed() {
        val currentChildFragmentManager = viewPagerAdapter.getItem(viewPagerMain.currentItem).childFragmentManager
        if (currentChildFragmentManager.backStackEntryCount != 0) {
            currentChildFragmentManager.popBackStack()
        } else {
            confirmExitDialog()
        }
    }

    private fun confirmExitDialog() {
        val alertDialog = AlertDialog.Builder(this)
            .setMessage(getString(R.string.confirm_exit_message))
            .setPositiveButton(getString(R.string.yes)) { dialog, which ->
                super.onBackPressed()
            }
            .setNegativeButton(getString(R.string.no)) { dialog, which ->
                dialog.dismiss()
            }
            .create()
        alertDialog.show()
    }
}
