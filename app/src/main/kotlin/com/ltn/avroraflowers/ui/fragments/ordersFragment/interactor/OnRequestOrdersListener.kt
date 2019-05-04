package com.ltn.avroraflowers.ui.fragments.ordersFragment.interactor

import com.ltn.avroraflowers.model.OrderItem
import com.ltn.avroraflowers.ui.base.BaseCallback

interface OnRequestOrdersListener: BaseCallback {
    fun onSuccessful(orders: List<OrderItem>)
}