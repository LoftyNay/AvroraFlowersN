package com.ltn.avroraflowers.ui.fragments.selectSavedOrderDIalogFragment.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ltn.avroraflowers.model.Repository.CartProductsRepository
import com.ltn.avroraflowers.model.SavedProduct
import com.ltn.avroraflowers.model.SavedProductKey
import com.ltn.avroraflowers.network.Response.SavedOrder
import com.ltn.avroraflowers.ui.base.BaseFragment
import com.ltn.avroraflowers.ui.fragments.selectSavedOrderDIalogFragment.interactor.OnAddSavedOrderInCart
import com.ltn.avroraflowers.ui.fragments.selectSavedOrderDIalogFragment.interactor.OnLoadSavedOrdersListener
import com.ltn.avroraflowers.ui.fragments.selectSavedOrderDIalogFragment.interactor.SelectSavedOrderDialogInteractor
import com.ltn.avroraflowers.ui.fragments.selectSavedOrderDIalogFragment.view.SelectSavedOrderDialogView

class SelectSavedOrderDialogPresenter(val view: SelectSavedOrderDialogView) {

    private var selectSavedOrderDialogInteractor = SelectSavedOrderDialogInteractor()

    fun loadSavedOrders() {
        selectSavedOrderDialogInteractor.requestSavedOrders(object : OnLoadSavedOrdersListener {
            override fun onSuccessful(result: HashMap<SavedProductKey, MutableList<SavedProduct>>) {
                view.showSavedOrders(result)
            }

            override fun onFailure(throwable: Throwable) {
                view.showConnectionProblem()
            }

            override fun onRequestEnded() {
                view.hideProgress()
            }

            override fun onRequestStart() {
                view.showProgress()
            }
        })
    }

    fun addSavedOrderToCart(saveOrderId: Int) {
        selectSavedOrderDialogInteractor.requestAddSavedOrderInCart(saveOrderId, object : OnAddSavedOrderInCart {
            override fun onSuccessful() {
                view.resultOk("Товары успешно добавлены в корзину")
                CartProductsRepository.getInstance().callUpdate()
            }

            override fun onFailure(throwable: Throwable) {
                view.showConnectionProblem()
            }

            override fun onRequestEnded() {
                view.hideLoadingDialog()
            }

            override fun onRequestStart() {
                view.showLoadingDialog()
            }
        })
    }
}