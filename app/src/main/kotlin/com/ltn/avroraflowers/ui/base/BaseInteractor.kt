package com.ltn.avroraflowers.ui.base

import com.ltn.avroraflowers.App
import com.ltn.avroraflowers.network.ApiAvrora
import javax.inject.Inject

abstract class BaseInteractor {

    @Inject
    lateinit var apiAvrora: ApiAvrora

    init {
        App.component.inject(this)
    }
}