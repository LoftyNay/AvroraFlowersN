package com.ltn.avroraflowers.ui.fragments.cartFragment.view

import com.ltn.avroraflowers.model.CartItem
import com.ltn.avroraflowers.ui.base.BaseView

interface CartFragmentView: BaseView {
    fun invalidateRecycler(invalidateType: Int)
    fun orderSended()
}