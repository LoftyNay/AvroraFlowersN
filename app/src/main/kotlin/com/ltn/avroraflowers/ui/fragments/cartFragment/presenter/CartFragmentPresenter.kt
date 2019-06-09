package com.ltn.avroraflowers.ui.fragments.cartFragment.presenter

import android.content.Context
import android.os.Handler
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.ltn.avroraflowers.App
import com.ltn.avroraflowers.adapters.CartProductsAdapter
import com.ltn.avroraflowers.model.CartItem
import com.ltn.avroraflowers.model.Repository.CartProductsRepository
import com.ltn.avroraflowers.model.Repository.OrdersRepository
import com.ltn.avroraflowers.ui.base.BasePresenter
import com.ltn.avroraflowers.ui.fragments.cartFragment.interactor.*
import com.ltn.avroraflowers.ui.fragments.cartFragment.view.CartFragmentView
import javax.inject.Inject

@InjectViewState
class CartFragmentPresenter : BasePresenter<CartFragmentView>(), ICartFragmentPresenter,
    CartProductsRepository.OnCartListener {

    @Inject
    lateinit var cartFragmentInteractor: CartFragmentInteractor

    @Inject
    lateinit var context: Context

    override fun attach() {
        App.component.inject(this)
        CartProductsRepository.getInstance().registerListener(this)
    }

    //Singleton ProductsRepository update call CartFragment
    override fun onUpdateProductsInCart() {
        cartFragmentInteractor.requestCartProducts(object : OnRequestCartProductsListener {
            override fun onRequestStart() {
                viewState.showProgress()
            }

            override fun onSuccessful(cartProducts: List<CartItem>) {
                CartProductsRepository.getInstance().addAllItems(cartProducts)
            }

            override fun onFailure(throwable: Throwable) {
                if (throwable !is IllegalStateException)
                    viewState.showConnectionProblem()
            }

            override fun onRequestEnded() {
                viewState.hideProgress()
                viewState.invalidateRecycler(CartProductsAdapter.INVALIDATE_TYPE_ADD)
            }
        })
    }

    fun saveCart(name: String) {
        cartFragmentInteractor.requestSaveCart(name, object : OnSaveCartListener {
            override fun onSuccessful() {
                viewState.resultOk("Заказ сохранен и может быть выбран для загрузки")
            }

            override fun onFailure(throwable: Throwable) {
                viewState.showConnectionProblem()
            }

            override fun onRequestEnded() {
                Handler().postDelayed({
                    viewState.hideLoadingDialog()
                }, 300)
            }

            override fun onRequestStart() {
                viewState.showLoadingDialog()
            }
        })
    }

    override fun sendOrder() {
        cartFragmentInteractor.requestSendOrder(object : OnSendOrderListener {
            override fun onRequestStart() {
                viewState.showLoadingDialog()
            }

            override fun onSuccessful() {
                OrdersRepository.getInstance().callUpdate()
                cartFragmentInteractor.requestDeleteProductsFromCart(object : OnDeleteCartProductsListener {
                    override fun onSuccessful() {
                        viewState.invalidateRecycler(CartProductsAdapter.INVALIDATE)
                        viewState.resultOk("Готово")
                    }

                    override fun onFailure(throwable: Throwable) {
                        viewState.showConnectionProblem()
                    }

                    override fun onRequestEnded() {
                        viewState.hideLoadingDialog()
                    }

                    override fun onRequestStart() {
                        viewState.showLoadingDialog()
                    }
                })
            }

            override fun onFailure(throwable: Throwable) {
                viewState.showConnectionProblem()
            }

            override fun onRequestEnded() {
                viewState.hideLoadingDialog()
            }
        })
    }

    override fun deleteProductsFromCart() {
        cartFragmentInteractor.requestDeleteProductsFromCart(object : OnDeleteCartProductsListener {
            override fun onRequestStart() {
                viewState.showLoadingDialog()
            }

            override fun onFailure(throwable: Throwable) {
                viewState.showConnectionProblem()
            }

            override fun onSuccessful() {
                viewState.invalidateRecycler(CartProductsAdapter.INVALIDATE)
                viewState.resultOk("Готово")
            }

            override fun onRequestEnded() {
                viewState.hideLoadingDialog()
            }
        })
    }

    override fun deleteProductsFromCart(listIds: MutableList<Int>) {
        cartFragmentInteractor.requestDeleteProductsFromCart(listIds, object : OnDeleteCartProductsListener {
            override fun onRequestStart() {
                viewState.showLoadingDialog()
            }

            override fun onFailure(throwable: Throwable) {
                viewState.showConnectionProblem()
            }

            override fun onSuccessful() {
                viewState.invalidateRecycler(CartProductsAdapter.INVALIDATE_TYPE_DELETE)
                viewState.resultOk("Готово")
            }

            override fun onRequestEnded() {
                Handler().postDelayed({
                    viewState.hideLoadingDialog()
                }, 300)
            }
        })
    }

    override fun detach() {}

    override fun destroy() {
        CartProductsRepository.getInstance().unregisterListener(this)
    }
}