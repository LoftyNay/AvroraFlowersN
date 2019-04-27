package com.ltn.avroraflowers.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentManagerUtils(var fragmentManager: FragmentManager) {

    fun replaceFragment(container: Int, fragment: Fragment, addBack: Boolean) {
        val fm = fragmentManager.beginTransaction()
        fm.replace(container, fragment)
        if (addBack) fm.addToBackStack("b_stack")
        fm.commit()
    }

    fun addFragment(container: Int, fragment: Fragment, addBack: Boolean) {
        val fm = fragmentManager.beginTransaction()
        fm.add(container, fragment)
        if (addBack) fm.addToBackStack("b_stack")
        fm.commit()
    }

    fun hideFragment(fragment: Fragment) {
        fragmentManager.beginTransaction().hide(fragment).commit()
    }

    fun showFragment(fragment: Fragment) {
        fragmentManager.beginTransaction().show(fragment).commit()
    }

    fun removeFragment(fragment: Fragment) {
        fragmentManager.beginTransaction().remove(fragment).commit()
    }
}