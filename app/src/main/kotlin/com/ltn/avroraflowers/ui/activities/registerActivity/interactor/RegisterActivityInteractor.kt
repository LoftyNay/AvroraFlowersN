package com.ltn.avroraflowers.ui.activities.registerActivity.interactor

import com.ltn.avroraflowers.network.RequestBody.UserRegister
import com.ltn.avroraflowers.ui.base.BaseInteractor
import com.ltn.avroraflowers.utils.StatusCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RegisterActivityInteractor : BaseInteractor(), IRegisterActivityInteractor {

    override fun registerUser(
        name: String,
        email: String,
        password: String,
        onRegisterUserListener: OnRegisterUserListener
    ) {
        val userData = UserRegister(name, email, password)
        disposable = apiAvrora.userRegister(userData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRegisterUserListener.onRequestStart() }
            .doFinally { onRegisterUserListener.onRequestEnded() }
            .subscribe(
                { result ->
                    when (result.code) {
                        StatusCode.USER_EXIST.value -> {
                            onRegisterUserListener.onUserExist()
                        }
                        StatusCode.CREATED.value -> {
                            onRegisterUserListener.onUserRegistered()
                        }
                    }
                    disposable.dispose()
                },
                {
                    onRegisterUserListener.onFailure(it)
                    disposable.dispose()
                }
            )
    }
}
