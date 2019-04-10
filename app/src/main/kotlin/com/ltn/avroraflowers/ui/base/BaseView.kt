package com.ltn.avroraflowers.ui.base

import android.content.Context
import com.arellomobile.mvp.MvpView
import com.ltn.avroraflowers.utils.Utils

interface BaseView : MvpView {
    fun showProgress()
    fun hideProgress()
    fun showConnectionProblem()
}