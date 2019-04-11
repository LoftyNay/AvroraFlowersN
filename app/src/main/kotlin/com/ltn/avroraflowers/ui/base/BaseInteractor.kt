package com.ltn.avroraflowers.ui.base

import com.ltn.avroraflowers.App
import com.ltn.avroraflowers.network.ApiAvrora
import com.ltn.avroraflowers.utils.FieldsValidator
import io.reactivex.disposables.Disposable
import javax.inject.Inject

abstract class BaseInteractor {

    protected lateinit var disposable: Disposable

    @Inject
    lateinit var apiAvrora: ApiAvrora

    init {
        App.component.inject(this)
    }
}