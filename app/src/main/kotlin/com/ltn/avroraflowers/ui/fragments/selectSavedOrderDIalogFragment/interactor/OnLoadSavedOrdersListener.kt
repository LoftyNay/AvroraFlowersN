package com.ltn.avroraflowers.ui.fragments.selectSavedOrderDIalogFragment.interactor

import com.ltn.avroraflowers.model.SavedProduct
import com.ltn.avroraflowers.model.SavedProductKey
import com.ltn.avroraflowers.network.Response.SavedOrder
import com.ltn.avroraflowers.ui.base.BaseCallback

interface OnLoadSavedOrdersListener: BaseCallback {
     fun onSuccessful(result: HashMap<SavedProductKey, MutableList<SavedProduct>>)
}