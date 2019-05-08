package com.ltn.avroraflowers.ui.fragments.innerProductFragment.interactor

import com.ltn.avroraflowers.model.Product
import com.ltn.avroraflowers.ui.base.BaseCallback

interface OnRequestProductListener : BaseCallback {
    fun onSuccessful(product: Product)
}