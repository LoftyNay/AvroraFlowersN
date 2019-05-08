package com.ltn.avroraflowers.ui.base

import android.content.Context
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.ltn.avroraflowers.App

abstract class BasePresenter<mvpView : MvpView> : MvpPresenter<mvpView>() {
    abstract fun attach()
    abstract fun detach()
    abstract fun destroy()
}