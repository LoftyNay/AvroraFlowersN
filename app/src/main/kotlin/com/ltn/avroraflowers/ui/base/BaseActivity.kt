package com.ltn.avroraflowers.ui.base

import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.ltn.avroraflowers.R

abstract class BaseActivity : MvpAppCompatActivity() {

    fun loadFragment(fragment: BaseFragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .commit()
    }
}