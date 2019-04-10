package com.ltn.avroraflowers.ui.fragments.entryLoginFragment.presenter

import android.content.Context
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.ltn.avroraflowers.App
import com.ltn.avroraflowers.ui.base.BasePresenter
import com.ltn.avroraflowers.ui.fragments.entryLoginFragment.interactor.EntryLoginInteractor
import com.ltn.avroraflowers.ui.fragments.entryLoginFragment.interactor.OnCheckUserDataFromServer
import com.ltn.avroraflowers.ui.fragments.entryLoginFragment.view.EntryLoginFragmentView
import com.ltn.avroraflowers.utils.Constants
import javax.inject.Inject

@InjectViewState
class EntryLoginFragmentPresenter : BasePresenter<EntryLoginFragmentView>(), IEntryLoginFragmentPresenter,
    OnCheckUserDataFromServer {

    @Inject
    lateinit var entryLoginInteractor: EntryLoginInteractor

    //LIFE CYCLE
    override fun attach(context: Context) {
        App.component.inject(this)
        Log.d(Constants.GLOBAL_LOG, "attach")
    }

    override fun detach() {
        Log.d(Constants.GLOBAL_LOG, "detach")
    }

    override fun destroy() {
        Log.d(Constants.GLOBAL_LOG, "Destroy")
    }

    //VALIDATE USERDATA
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