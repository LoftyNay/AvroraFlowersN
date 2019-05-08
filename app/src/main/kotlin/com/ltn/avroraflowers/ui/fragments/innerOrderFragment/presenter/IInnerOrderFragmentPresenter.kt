package com.ltn.avroraflowers.ui.fragments.innerOrderFragment.presenter

import com.ltn.avroraflowers.ui.base.BasePresenter
import com.ltn.avroraflowers.ui.fragments.innerOrderFragment.view.InnerOrderFragmentView

interface IInnerOrderFragmentPresenter {
    fun getOrderInfo(id: Int)
}