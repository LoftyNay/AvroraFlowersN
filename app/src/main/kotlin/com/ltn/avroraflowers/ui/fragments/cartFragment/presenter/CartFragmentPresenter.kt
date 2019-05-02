package com.ltn.avroraflowers.ui.fragments.cartFragment.presenter

import android.content.Context
import androidx.work.WorkManager
import com.arellomobile.mvp.InjectViewState
import com.ltn.avroraflowers.App
import com.ltn.avroraflowers.model.CartItem
import com.ltn.avroraflowers.ui.base.BasePresenter
import com.ltn.avroraflowers.ui.fragments.cartFragment.interactor.CartFragmentInteractor
import com.ltn.avroraflowers.ui.fragments.cartFragment.interactor.DeleteProductsFromCartService
import com.ltn.avroraflowers.ui.fragments.cartFragment.interactor.OnRequestCartProductsListener
import com.ltn.avroraflowers.ui.fragments.cartFragment.view.CartFragmentView
import java.util.ArrayList
import javax.inject.Inject

@InjectViewState
class CartFragmentPresenter : BasePresenter<CartFragmentView>(), ICartFragmentPresenter {

    @Inject
    lateinit var cartFragmentInteractor: CartFragmentInteractor

    @Inject
    lateinit var context: Context

    override fun attach(context: Context) {
        App.component.inject(this)
    }

    override fun getCartProducts() {
        viewState.showProgress()
        cartFragmentInteractor.requestCartProducts(object : OnRequestCartProductsListener {
            override fun onSuccessful(cartProducts: List<CartItem>) {
                viewState.showCartProducts(cartProducts)
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
        val intent = DeleteProductsFromCartService.getIntent(context)
        intent.putIntegerArrayListExtra("ids", listIds as ArrayList<Int>)
        context.startService(intent)
    }

    override fun detach() {
    }

    override fun destroy() {
    }
}