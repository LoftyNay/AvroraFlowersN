package com.ltn.avroraflowers.ui.fragments.innerProductFragment.view

import com.ltn.avroraflowers.model.Product
import com.ltn.avroraflowers.ui.base.BaseView

interface InnerProductFragmentView : BaseView {
    fun showProductInfo(product: Product)
    fun showLoadingDialog()
    fun hideLoadingDialog()
}