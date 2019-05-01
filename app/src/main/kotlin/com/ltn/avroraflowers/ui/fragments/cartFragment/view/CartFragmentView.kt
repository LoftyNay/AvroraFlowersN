package com.ltn.avroraflowers.ui.fragments.cartFragment.view

import com.ltn.avroraflowers.model.CartItem
import com.ltn.avroraflowers.ui.base.BaseView

interface CartFragmentView: BaseView {
    fun showCartProducts(cartProducts: List<CartItem>)
}