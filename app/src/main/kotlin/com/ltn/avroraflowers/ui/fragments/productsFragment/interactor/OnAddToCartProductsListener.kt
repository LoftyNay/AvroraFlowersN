package com.ltn.avroraflowers.ui.fragments.productsFragment.interactor

import com.ltn.avroraflowers.ui.base.BaseCallback

interface OnAddToCartProductsListener: BaseCallback {
    fun onSuccessful(productName: String)
}