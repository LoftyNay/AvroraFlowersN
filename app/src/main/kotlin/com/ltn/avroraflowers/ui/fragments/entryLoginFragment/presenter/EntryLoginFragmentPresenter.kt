package com.ltn.avroraflowers.ui.fragments.entryLoginFragment.presenter

import android.content.Context
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.ltn.avroraflowers.App
import com.ltn.avroraflowers.ui.base.BasePresenter
import com.ltn.avroraflowers.ui.fragments.entryLoginFragment.interactor.EntryLoginInteractor
import com.ltn.avroraflowers.ui.fragments.entryLoginFragment.interactor.OnCheckUserDataListener
import com.ltn.avroraflowers.ui.fragments.entryLoginFragment.view.EntryLoginFragmentView
import com.ltn.avroraflowers.utils.Constants
import javax.inject.Inject

@InjectViewState
class EntryLoginFragmentPresenter : BasePresenter<EntryLoginFragmentView>(), IEntryLoginFragmentPresenter,
    OnCheckUserDataListener {

    @Inject
    lateinit var entryLoginInteractor: EntryLoginInteractor

    override fun attach() {
        App.component.inject(this)
    }

    override fun detach() {
    }

    override fun destroy() {
        Log.d(Constants.GLOBAL_LOG, "Destroy")
    }

    override fun validateUserData(email: String, password: String) {
        viewState.showProgress()
        entryLoginInteractor.checkUserDataFromServer(email, password, this)
    }

    override fun onFailure() {
        viewState.showConnectionProblem()
    }

    override fun onValidUserData() {
        viewState.userDataValidationOk()
    }

    override fun onEmailNotFound() {
        viewState.showEmailNotFound()
    }

    override fun onWrongPassword() {
        viewState.showWrongPassword()
    }

    override fun onCheckEnded() {
        viewState.hideProgress()
    }
}