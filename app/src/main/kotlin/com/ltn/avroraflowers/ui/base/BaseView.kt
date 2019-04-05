package com.ltn.avroraflowers.ui.base

import com.arellomobile.mvp.MvpView

interface BaseView : MvpView {
    fun showProgress()
    fun hideProgress()
}