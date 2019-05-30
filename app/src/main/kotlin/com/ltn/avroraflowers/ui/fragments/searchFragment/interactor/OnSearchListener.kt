package com.ltn.avroraflowers.ui.fragments.searchFragment.interactor

import com.ltn.avroraflowers.model.Product
import com.ltn.avroraflowers.ui.base.BaseCallback

interface OnSearchListener: BaseCallback {
    fun onSuccessful(result: List<Product>)
}