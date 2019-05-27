package com.ltn.avroraflowers.ui.base

import androidx.fragment.app.Fragment
import com.arellomobile.mvp.MvpAppCompatFragment
import com.ltn.avroraflowers.R

abstract class BaseManagerFragment : MvpAppCompatFragment() {

    fun showFragmentInMainContainer(fragment: Fragment) {
        if (fragmentManager != null) {
            fragmentManager?.beginTransaction()?.hide(this)!!.add(R.id.mainContainer, fragment, fragment.tag)
                .show(fragment).commit()
        }
    }
}