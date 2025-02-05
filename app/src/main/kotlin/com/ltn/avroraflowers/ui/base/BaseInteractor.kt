package com.ltn.avroraflowers.ui.base

import com.ltn.avroraflowers.App
import com.ltn.avroraflowers.network.ApiAvrora
import com.ltn.avroraflowers.utils.FieldsValidator
import com.ltn.avroraflowers.utils.PreferencesUtils
import io.reactivex.disposables.Disposable
import javax.inject.Inject

abstract class BaseInteractor {

    protected lateinit var disposable: Disposable

    @Inject
    lateinit var apiAvrora: ApiAvrora

    @Inject
    lateinit var preferencesUtils: PreferencesUtils

    init {
        App.component.inject(this)
    }
}