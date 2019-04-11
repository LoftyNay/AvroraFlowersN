package com.ltn.avroraflowers.ui.activities.registerActivity.view

import android.view.View
import com.ltn.avroraflowers.ui.base.BaseView

interface RegisterActivityView: BaseView {
    fun showUserRegisteredOk()
    fun showUserExist()
    fun showWrongInputErrorMessage(v: View)
}