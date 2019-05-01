package com.ltn.avroraflowers.ui.fragments.cartFragment.presenter

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.ltn.avroraflowers.App
import com.ltn.avroraflowers.model.CartItem
import com.ltn.avroraflowers.ui.base.BasePresenter
import com.ltn.avroraflowers.ui.fragments.cartFragment.interactor.CartFragmentInteractor
import com.ltn.avroraflowers.ui.fragments.cartFragment.interactor.OnRequestCartProductsListener
import com.ltn.avroraflowers.ui.fragments.cartFragment.view.CartFragmentView
import javax.inject.Inject

@InjectViewState
class CartFragmentPresenter: BasePresenter<CartFragmentView>(), ICartFragmentPresenter, OnRequestCartProductsListener  {

    @Inject
    lateinit var cartFragmentInteractor: CartFragmentInteractor

    override fun attach(context: Context) {
        App.component.inject(this)
    }

    override fun getCartProducts() {
        viewState.showProgress()
        cartFragmentInteractor.requestCartProducts(this)
    }

    override fun onSuccessful(cartProducts: List<CartItem>) {
        viewState.showCartProducts(cartProducts)
    }

    override fun onFailure() {
        viewState.showConnectionProblem()
    }

    override fun onRequestEnded() {
        viewState.hideProgress()
    }

    override fun detach() {
    }

    override fun destroy() {
    }
}