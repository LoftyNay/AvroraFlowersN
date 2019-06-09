package com.ltn.avroraflowers.ui.fragments.mainFragment.view

import com.ltn.avroraflowers.ui.base.BaseView

interface MainFragmentView : BaseView {
    fun navigateToCart()
    fun showLoadingDialog()
    fun hideLoadingDialog()
}