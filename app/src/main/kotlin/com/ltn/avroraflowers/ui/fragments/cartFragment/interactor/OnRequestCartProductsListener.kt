package com.ltn.avroraflowers.ui.fragments.cartFragment.interactor

import com.ltn.avroraflowers.model.CartItem
import com.ltn.avroraflowers.ui.base.BaseCallback

interface OnRequestCartProductsListener: BaseCallback {
    fun onSuccessful(cartProducts: List<CartItem>)
}