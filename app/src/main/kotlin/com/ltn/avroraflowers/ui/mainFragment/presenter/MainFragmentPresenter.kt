package com.ltn.avroraflowers.ui.mainFragment.presenter

import android.os.Handler
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
                view.showLoadingDialog()
            }

            override fun onSuccessful() {
                mainFragmentInteractor.loadProductsInCart(object : OnLoadLastOrderInCart {
                    override fun onSuccessful() {
                        CartProductsRepository.getInstance().callUpdate()
                        view.resultOk("Заказ успешно загружен в корзину")
                        view.navigateToCart()
                    }

                    override fun onFailure(throwable: Throwable) {
                        view.showConnectionProblem()
                    }

                    override fun onRequestEnded() {
                        Handler().postDelayed({
                            view.hideLoadingDialog()
                        }, 300)
                    }

                    override fun onRequestStart() {
                        view.showLoadingDialog()
                    }
                })
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