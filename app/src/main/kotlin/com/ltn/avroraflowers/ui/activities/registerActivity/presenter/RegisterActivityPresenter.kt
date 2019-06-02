package com.ltn.avroraflowers.ui.activities.registerActivity.presenter

import android.content.Context
import android.view.View
import com.arellomobile.mvp.InjectViewState
import com.google.android.material.textfield.TextInputEditText
import com.ltn.avroraflowers.App
import com.ltn.avroraflowers.ui.activities.registerActivity.interactor.OnRegisterUserListener
import com.ltn.avroraflowers.ui.activities.registerActivity.interactor.RegisterActivityInteractor
import com.ltn.avroraflowers.ui.activities.registerActivity.view.RegisterActivityView
import com.ltn.avroraflowers.ui.base.BasePresenter
import com.ltn.avroraflowers.utils.FieldsValidator
import javax.inject.Inject

@InjectViewState
class RegisterActivityPresenter : BasePresenter<RegisterActivityView>(), IRegisterActivityPresenter,
    OnRegisterUserListener {

    @Inject
    lateinit var fieldsValidator: FieldsValidator

    @Inject
    lateinit var registerActivityInteractor: RegisterActivityInteractor

    override fun attach() {
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
        when {
            !fieldsValidator.isNameValid(nameEditText) -> {
                viewState.showWrongInputErrorMessage(nameEditText.parent.parent as View)
            }
            !fieldsValidator.isEmailValid(emailEditText) -> {
                viewState.showWrongInputErrorMessage(emailEditText.parent.parent as View)
            }
            !fieldsValidator.isPasswordValid(passwordEditText) -> {
                viewState.showWrongInputErrorMessage(passwordEditText.parent.parent as View)
            }
            else -> {
                registerActivityInteractor.registerUser(
                    nameEditText.text?.trim().toString(),
                    emailEditText.text?.trim().toString(),
                    passwordEditText.text?.trim().toString(),
                    this
                )
            }
        }
    }

    override fun onRequestStart() {
        viewState.showProgress()
    }

    override fun onWrongInput(v: View) {
        viewState.showWrongInputErrorMessage(v)
    }

    override fun onFailure(throwable: Throwable) {
        viewState.showConnectionProblem()
    }

    override fun onUserRegistered() {
        viewState.showUserRegisteredOk()
    }

    override fun onUserExist() {
        viewState.showUserExist()
    }

    override fun onRequestEnded() {
        viewState.hideProgress()
    }
}