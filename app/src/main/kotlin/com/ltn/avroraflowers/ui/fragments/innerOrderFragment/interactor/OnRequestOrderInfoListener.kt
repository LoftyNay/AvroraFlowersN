package com.ltn.avroraflowers.ui.fragments.innerOrderFragment.interactor

import com.ltn.avroraflowers.model.OrderInner
import com.ltn.avroraflowers.ui.base.BaseCallback

interface OnRequestOrderInfoListener: BaseCallback {
    fun onSuccessful(list: List<OrderInner>)
}