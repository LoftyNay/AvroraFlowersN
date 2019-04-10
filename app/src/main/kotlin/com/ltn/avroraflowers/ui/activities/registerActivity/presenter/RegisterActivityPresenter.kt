package com.ltn.avroraflowers.ui.activities.registerActivity.presenter

import android.content.Context
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.google.android.material.textfield.TextInputEditText
import com.ltn.avroraflowers.App
import com.ltn.avroraflowers.ui.activities.registerActivity.interactor.OnRegisterUser
import com.ltn.avroraflowers.ui.activities.registerActivity.interactor.RegisterActivityInteractor
import com.ltn.avroraflowers.ui.activities.registerActivity.view.RegisterActivityView
import com.ltn.avroraflowers.ui.base.BasePresenter
import com.ltn.avroraflowers.utils.Constants
import javax.inject.Inject

@InjectViewState
class RegisterActivityPresenter : BasePresenter<RegisterActivityView>(), IRegisterActivityPresenter,
    OnRegisterUser {

    @Inject
    lateinit var registerActivityInteractor: RegisterActivityInteractor

    override fun attach(context: Context) {
        App.component.inject(this)
    }

    override fun detach() {
    }

    override fun destroy() {
    }

    override fun toRegisterUser(
        nameEditText: TextInputEditText,
        emailEditText: TextInputEditText,
        passwordEditText: TextInputEditText
    ) {
        viewState.showProgress()
        registerActivityInteractor.validateAndRegisterUser(nameEditText, emailEditText, passwordEditText, this)
    }

    override fun onFailure() {
        viewState.showConnectionProblem()
    }

    override fun onValidateAndRegisterUserEnded() {
        viewState.hideProgress()
    }

    override fun onUserRegistered() {
        viewState.showUserRegisteredOk()
    }

    override fun onUserExist() {
        viewState.showUserExist()
    }
}