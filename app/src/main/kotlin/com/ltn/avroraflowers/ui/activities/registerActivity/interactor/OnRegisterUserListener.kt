package com.ltn.avroraflowers.ui.activities.registerActivity.interactor

import android.view.View
import com.ltn.avroraflowers.ui.base.BaseCallback

interface OnRegisterUserListener: BaseCallback {
    fun onUserRegistered()
    fun onUserExist()
    fun onWrongInput(v: View)
}