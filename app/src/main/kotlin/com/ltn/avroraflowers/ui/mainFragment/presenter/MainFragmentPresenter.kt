package com.ltn.avroraflowers.ui.mainFragment.presenter

import android.util.Log
import com.ltn.avroraflowers.model.Repository.CartProductsRepository
import com.ltn.avroraflowers.ui.mainFragment.interactor.MainFragmentInteractor
import com.ltn.avroraflowers.ui.mainFragment.interactor.OnLoadLastOrderInCart
import com.ltn.avroraflowers.ui.mainFragment.view.MainFragmentView

class MainFragmentPresenter(private val view: MainFragmentView) {

    private val mainFragmentInteractor = MainFragmentInteractor()

    fun repeatLastOrder() {
        mainFragmentInteractor.requestLoadLastOrderInCart(object : OnLoadLastOrderInCart {
            override fun onRequestStart() {
                view.showProgress()
            }

            override fun onSuccessful() {
                view.resultOk("Заказ успешно загружен в корзину")
                CartProductsRepository.getInstance().callUpdate()
            }

            override fun onFailure(throwable: Throwable) {
                Log.d("GLL", throwable.message)
                view.showConnectionProblem()
            }

            override fun onRequestEnded() {
                view.hideProgress()
            }
        })
    }
}