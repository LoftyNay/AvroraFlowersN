package com.ltn.avroraflowers.ui.activities

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.ui.base.BaseActivity
import com.ltn.avroraflowers.ui.fragments.CartFragment
import com.ltn.avroraflowers.ui.fragments.catalogFragment.CatalogFragment
import com.ltn.avroraflowers.ui.fragments.MainFragment
import com.ltn.avroraflowers.ui.fragments.OrdersFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.hide()

        bottomNavigation.setOnNavigationItemSelectedListener(this)
        loadFragment(MainFragment())
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bottom_navigation_main -> {
                loadFragment(MainFragment())
                supportActionBar?.hide()
            }
            R.id.bottom_navigation_cart -> {
                loadFragment(CartFragment())
                supportActionBar?.show()
                supportActionBar?.title = resources.getString(R.string.cart_item_nav)
            }
            R.id.bottom_navigation_catalog -> {
                loadFragment(CatalogFragment())
                supportActionBar?.show()
                supportActionBar?.title = resources.getString(R.string.catalog_item_nav)
            }
            R.id.bottom_navigation_orders -> {
                loadFragment(OrdersFragment())
                supportActionBar?.show()
                supportActionBar?.title = resources.getString(R.string.orders_item_nav)
            }
        }
        return true
    }
}
