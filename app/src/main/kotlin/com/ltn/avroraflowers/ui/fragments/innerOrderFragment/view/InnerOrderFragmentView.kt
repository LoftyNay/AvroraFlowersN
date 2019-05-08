package com.ltn.avroraflowers.ui.fragments.innerOrderFragment.view

import com.ltn.avroraflowers.model.OrderInner
import com.ltn.avroraflowers.ui.base.BaseView

interface InnerOrderFragmentView: BaseView {
    fun showProductsInOrder(list: List<OrderInner>)
}