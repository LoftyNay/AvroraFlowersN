package com.ltn.avroraflowers.ui.activities

import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.ltn.avroraflowers.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        navigationSetup()
    }

    private fun navigationSetup() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottomNavigation.setupWithNavController(navController)
        navController.addOnDestinationChangedListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        if (destination.id == R.id.navigation_main) {
            supportActionBar?.hide()
        } else {
            supportActionBar?.title = destination.label
            supportActionBar?.show()
        }
    }

    /*
        toolbarHide = toolbar.animate().translationY((-toolbar.bottom).toFloat()).setInterpolator(AccelerateInterpolator())
        toolbarShow = toolbar.animate().translationY(0F).setInterpolator(DecelerateInterpolator())*/
}
