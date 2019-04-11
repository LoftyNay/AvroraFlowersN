package com.ltn.avroraflowers.ui.base

import android.content.Context
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView

abstract class BasePresenter<mvpView : MvpView> : MvpPresenter<mvpView>() {
    abstract fun attach(context: Context)
    abstract fun detach()
    abstract fun destroy()
}