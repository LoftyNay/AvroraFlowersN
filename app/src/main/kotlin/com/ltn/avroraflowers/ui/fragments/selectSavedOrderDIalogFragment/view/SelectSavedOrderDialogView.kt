package com.ltn.avroraflowers.ui.fragments.selectSavedOrderDIalogFragment.view

import com.ltn.avroraflowers.model.SavedProduct
import com.ltn.avroraflowers.model.SavedProductKey
import com.ltn.avroraflowers.network.Response.SavedOrder
import com.ltn.avroraflowers.ui.base.BaseView

interface SelectSavedOrderDialogView: BaseView {
    fun showSavedOrders(savedOrders: HashMap<SavedProductKey, MutableList<SavedProduct>>)
    fun invalidateRecycler()
    fun showLoadingDialog()
    fun hideLoadingDialog()
}