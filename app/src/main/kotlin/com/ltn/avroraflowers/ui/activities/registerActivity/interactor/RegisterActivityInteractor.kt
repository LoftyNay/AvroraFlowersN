package com.ltn.avroraflowers.ui.activities.registerActivity.interactor

import android.util.Log
import com.ltn.avroraflowers.model.UserRegister
import com.ltn.avroraflowers.ui.base.BaseInteractor
import com.ltn.avroraflowers.utils.Constants
import com.ltn.avroraflowers.utils.STATUS_CODE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RegisterActivityInteractor : BaseInteractor(), IRegisterActivityInteractor {

    override fun registerUser(
        email: String,
        password: String,
        onRegisterUserListener: OnRegisterUserListener
    ) {
        val userData = UserRegister(email, password)
        disposable = apiAvrora.userRegister(userData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    when (result.code) {
                        STATUS_CODE.USER_EXIST.value -> {
                            onRegisterUserListener.onUserExist()
                        }
                        STATUS_CODE.CREATED.value -> {
                            onRegisterUserListener.onUserRegistered()
                        }
                    }
                    disposable.dispose()
                    onRegisterUserListener.onRegisterUserEnded()
                },
                { error ->
                    Log.d(Constants.GLOBAL_LOG, error.message)
                    disposable.dispose()
                    onRegisterUserListener.onFailure()
                    onRegisterUserListener.onRegisterUserEnded()
                }
            )
    }
}
