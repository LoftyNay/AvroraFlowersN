package com.ltn.avroraflowers.ui.fragments.entryLoginFragment.interactor

import android.util.Log
import com.ltn.avroraflowers.network.RequestBody.UserLogin
import com.ltn.avroraflowers.ui.base.BaseInteractor
import com.ltn.avroraflowers.utils.StatusCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EntryLoginInteractor : BaseInteractor(), IEntryLoginInteractor {

    override fun checkUserDataFromServer(
        email: String,
        password: String,
        onCheckUserDataListener: OnCheckUserDataListener
    ) {
        disposable = apiAvrora.userLogin(UserLogin(email, password))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    when (result.code) {
                        StatusCode.OK.value -> {
                            onCheckUserDataListener.onValidUserData(result)
                        }
                        StatusCode.USER_NOT_FOUND.value -> {
                            onCheckUserDataListener.onEmailNotFound()
                        }
                        StatusCode.WRONG_PASSWORD.value -> {
                            onCheckUserDataListener.onWrongPassword()
                        }
                    }
                    disposable.dispose()
                    onCheckUserDataListener.onCheckEnded()
                },
                { error ->
                    disposable.dispose()
                    Log.d("GLL", error.message)
                    onCheckUserDataListener.onCheckEnded()
                    onCheckUserDataListener.onFailure()
                }
            )
    }
}