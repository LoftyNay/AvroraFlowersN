package com.ltn.avroraflowers.ui.fragments.cartFragment.presenter

import android.content.Context
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.ltn.avroraflowers.App
import com.ltn.avroraflowers.adapters.CartProductsAdapter
import com.ltn.avroraflowers.model.CartItem
import com.ltn.avroraflowers.model.Repository.CartProductsRepository
import com.ltn.avroraflowers.model.Repository.OrdersRepository
import com.ltn.avroraflowers.ui.base.BasePresenter
import com.ltn.avroraflowers.ui.fragments.cartFragment.interactor.CartFragmentInteractor
import com.ltn.avroraflowers.ui.fragments.cartFragment.interactor.OnDeleteCartProductsListener
import com.ltn.avroraflowers.ui.fragments.cartFragment.interactor.OnRequestCartProductsListener
import com.ltn.avroraflowers.ui.fragments.cartFragment.interactor.OnSendOrderListener
import com.ltn.avroraflowers.ui.fragments.cartFragment.view.CartFragmentView
import javax.inject.Inject

@InjectViewState
class CartFragmentPresenter : BasePresenter<CartFragmentView>(), ICartFragmentPresenter,
    CartProductsRepository.OnUpdateCartListener {

    @Inject
    lateinit var cartFragmentInteractor: CartFragmentInteractor

    @Inject
    lateinit var context: Context

    override fun attach(context: Context) {
        App.component.inject(this)
        CartProductsRepository.getInstance().registerListener(this)
    }

    override fun sendOrder() {
        cartFragmentInteractor.requestSendOrder(object : OnSendOrderListener {
            override fun onRequestStart() {
                viewState.showProgress()
            }

            override fun onSuccessful() {
                //fixme
                OrdersRepository.getInstance().callUpdate()
                //viewState.invalidateRecycler(CartProductsAdapter.INVALIDATE_TYPE_DELETE)
            }

            override fun onFailure() {
                viewState.showConnectionProblem()
            }

            override fun onRequestEnded() {
                viewState.hideProgress()
            }
        })
    }

    override fun deleteProductsFromCart(listIds: MutableList<Int>) {
        cartFragmentInteractor.requestDeleteProductsFromCart(listIds, object : OnDeleteCartProductsListener {
            override fun onRequestStart() {
                viewState.showProgress()
            }

            override fun onFailure() {
                viewState.showConnectionProblem()
                Log.d("GLL", "FAIL")
            }

            override fun onSuccessful() {
                viewState.invalidateRecycler(CartProductsAdapter.INVALIDATE_TYPE_DELETE)
            }

            override fun onRequestEnded() {
                viewState.hideProgress()
            }
        })
    }

    //Singleton ProductsRepository update call CartFragment
    override fun onUpdateProductsInCart() {
        cartFragmentInteractor.requestCartProducts(object : OnRequestCartProductsListener {
            override fun onRequestStart() {
                viewState.showProgress()
            }

            override fun onSuccessful(cartProducts: List<CartItem>) {
                CartProductsRepository.getInstance().addAllItems(cartProducts)
                viewState.invalidateRecycler(CartProductsAdapter.INVALIDATE_TYPE_ADD)
            }

            override fun onFailure() {
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