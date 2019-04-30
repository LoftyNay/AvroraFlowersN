package com.ltn.avroraflowers.ui.fragments.productsFragment.interactor

import com.ltn.avroraflowers.model.Product
import com.ltn.avroraflowers.ui.base.BaseCallback

interface OnRequestProductsListener : BaseCallback {
    fun onSuccessful(products: List<Product>)
}