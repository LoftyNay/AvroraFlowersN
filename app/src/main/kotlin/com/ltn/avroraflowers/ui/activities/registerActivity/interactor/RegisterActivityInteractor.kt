package com.ltn.avroraflowers.ui.activities.registerActivity.interactor

import android.util.Log
import com.google.android.material.textfield.TextInputEditText
import com.ltn.avroraflowers.model.UserRegister
import com.ltn.avroraflowers.ui.base.BaseInteractor
import com.ltn.avroraflowers.utils.Constants
import com.ltn.avroraflowers.utils.STATUS_CODE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RegisterActivityInteractor : BaseInteractor(), IRegisterActivityInteractor {

    override fun validateAndRegisterUser(
        name: TextInputEditText,
        email: TextInputEditText,
        password: TextInputEditText,
        onRegisterUser: OnRegisterUser
    ) {
        if (fieldsValidator.isNameValid(name) && fieldsValidator.isEmailValid(email)
            && fieldsValidator.isPasswordValid(password)
        ) {
            disposable = apiAvrora.userRegister(UserRegister(email.text.toString(), password.text.toString()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        when (result.code) {
                            STATUS_CODE.USER_EXIST.value -> {
                                onRegisterUser.onUserExist()
                            }
                            STATUS_CODE.CREATED.value -> {
                                onRegisterUser.onUserRegistered()
                            }
                        }
                        disposable.dispose()
                        onRegisterUser.onValidateAndRegisterUserEnded()
                    },
                    { error ->
                        Log.d(Constants.GLOBAL_LOG, error.message)
                        disposable.dispose()
                        onRegisterUser.onFailure()
                        onRegisterUser.onValidateAndRegisterUserEnded()
                    }
                )
        } else {
            onRegisterUser.onValidateAndRegisterUserEnded()
        }
    }
}