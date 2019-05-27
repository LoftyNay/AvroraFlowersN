package com.ltn.avroraflowers.ui.fragments.entryLoginFragment.view

import com.arellomobile.mvp.MvpView
import com.ltn.avroraflowers.network.Response.LoginResponse
import com.ltn.avroraflowers.ui.base.BaseView

interface EntryLoginFragmentView: BaseView {
    fun userDataValidationOk(profile: LoginResponse)
    fun showEmailNotFound()
    fun showWrongPassword()
}