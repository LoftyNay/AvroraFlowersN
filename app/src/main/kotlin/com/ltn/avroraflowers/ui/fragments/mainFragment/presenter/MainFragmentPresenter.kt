package com.ltn.avroraflowers.ui.fragments.mainFragment.presenter

import com.ltn.avroraflowers.model.Repository.CartProductsRepository
import com.ltn.avroraflowers.ui.fragments.mainFragment.interactor.MainFragmentInteractor
import com.ltn.avroraflowers.ui.fragments.mainFragment.interactor.OnLoadLastOrderInCart
import com.ltn.avroraflowers.ui.fragments.mainFragment.view.MainFragmentView
import com.ltn.avroraflowers.utils.StatusCode

class MainFragmentPresenter(private val view: MainFragmentView) {

    private val mainFragmentInteractor = MainFragmentInteractor()

    fun repeatLastOrder() {
        mainFragmentInteractor.requestLoadLastOrderInCart(object : OnLoadLastOrderInCart {
            override fun onRequestStart() {
                view.showLoadingDialog()
            }

            override fun onSuccessful(code: Int) {
                when(code) {
                    StatusCode.OK.value -> {
                        CartProductsRepository.getInstance().callUpdate()
                        view.resultOk("Заказ успешно загружен в корзину")
                        view.navigateToCart()
                    }
                    StatusCode.NO_CONTENT.value -> {
                        view.resultOk("Вы еще ничего не заказывали")
                    }
                }
            }

            override fun onFailure(throwable: Throwable) {
                view.showConnectionProblem()
            }

            override fun onRequestEnded() {
                view.hideLoadingDialog()
            }
        })
    }
}