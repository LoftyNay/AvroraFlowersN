package com.ltn.avroraflowers.ui.fragments.entryLoginFragment.interactor

import android.util.Log
import com.ltn.avroraflowers.model.UserLogin
import com.ltn.avroraflowers.ui.base.BaseInteractor
import com.ltn.avroraflowers.utils.Constants
import com.ltn.avroraflowers.utils.STATUS_CODE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class EntryLoginInteractor : BaseInteractor(), IEntryLoginInteractor {

    private lateinit var disposable: Disposable

    override fun checkUserDataFromServer(
        email: String,
        password: String,
        onCheckUserDataFromServer: OnCheckUserDataFromServer
    ) {
        disposable = apiAvrora.userLogin(UserLogin(email, password))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    when (result.code) {
                        STATUS_CODE.OK.value -> {
                            onCheckUserDataFromServer.onValidUserData()
                        }
                        STATUS_CODE.USER_NOT_FOUND.value -> {
                            onCheckUserDataFromServer.onEmailNotFound()
                        }
                        STATUS_CODE.WRONG_PASSWORD.value -> {
                            onCheckUserDataFromServer.onWrongPassword()
                        }
                    }
                    disposable.dispose()
                },
                { error ->
                    Log.d(Constants.GLOBAL_LOG, error.message)
                    disposable.dispose()
                }
            )
    }
}