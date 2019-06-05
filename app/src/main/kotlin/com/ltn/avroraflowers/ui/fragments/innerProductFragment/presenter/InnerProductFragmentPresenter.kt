package com.ltn.avroraflowers.ui.fragments.innerProductFragment.presenter

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.ltn.avroraflowers.App
import com.ltn.avroraflowers.model.Product
import com.ltn.avroraflowers.model.Repository.CartProductsRepository
import com.ltn.avroraflowers.ui.base.BasePresenter
import com.ltn.avroraflowers.ui.fragments.innerProductFragment.interactor.InnerProductFragmentInteractor
import com.ltn.avroraflowers.ui.fragments.innerProductFragment.interactor.OnRequestProductListener
import com.ltn.avroraflowers.ui.fragments.innerProductFragment.view.InnerProductFragmentView
import com.ltn.avroraflowers.ui.fragments.productsFragment.interactor.OnAddToCartProductsListener
import javax.inject.Inject

@InjectViewState
class InnerProductFragmentPresenter: BasePresenter<InnerProductFragmentView>(), IInnerProductFragmentPresenter {

    @Inject
    lateinit var innerProductFragmentInteractor: InnerProductFragmentInteractor

    override fun attach() {
        App.component.inject(this)
    }

    fun addToCart(id: Int, count: Int, perPack: Int) {
        innerProductFragmentInteractor.requestAddToCart(id, count, perPack, object: OnAddToCartProductsListener {
            override fun onSuccessful() {
                viewState.resultOk("Добавлен в корзину")
            }

            override fun onFailure(throwable: Throwable) {
                viewState.showConnectionProblem()
            }

            override fun onRequestEnded() {
                CartProductsRepository.getInstance().callUpdate()
            }

            override fun onRequestStart() {

            }
        })
    }

    override fun getProduct(id: Int) {
        innerProductFragmentInteractor.requestProductFromServer(id, object : OnRequestProductListener {
            override fun onRequestStart() {
                viewState.showProgress()
            }

            override fun onSuccessful(product: Product) {
                viewState.showProductInfo(product)
            }

            override fun onFailure(throwable: Throwable) {
                viewState.showConnectionProblem()
            }

            override fun onRequestEnded() {
                viewState.hideProgress()
            }
        })
    }

    override fun detach() {
    }

    override fun destroy() {
    }
}