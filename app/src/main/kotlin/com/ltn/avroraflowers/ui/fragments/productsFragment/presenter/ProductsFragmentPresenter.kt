package com.ltn.avroraflowers.ui.fragments.productsFragment.presenter

import android.content.Context
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.ltn.avroraflowers.App
import com.ltn.avroraflowers.model.Product
import com.ltn.avroraflowers.ui.base.BasePresenter
import com.ltn.avroraflowers.ui.fragments.productsFragment.interactor.OnAddToCartProductsListener
import com.ltn.avroraflowers.ui.fragments.productsFragment.interactor.OnRequestProductsListener
import com.ltn.avroraflowers.ui.fragments.productsFragment.interactor.ProductsFragmentInteractor
import com.ltn.avroraflowers.ui.fragments.productsFragment.view.ProductsFragmentView
import javax.inject.Inject

@InjectViewState
class ProductsFragmentPresenter : BasePresenter<ProductsFragmentView>(), IProductsFragmentPresenter {

    @Inject
    lateinit var productsFragmentInteractor: ProductsFragmentInteractor

    override fun attach(context: Context) {
        App.component.inject(this)
    }

    override fun getProductsFromServerByCategoryId(id: Int) {
        viewState.showProgress()
        productsFragmentInteractor.requestProductsByCategoryId(id, object : OnRequestProductsListener {
            override fun onSuccessful(products: List<Product>) {
                viewState.showProducts(products)
            }

            override fun onFailure() {
                Log.d("GLL", "fail")
            }

            override fun onRequestEnded() {
                viewState.hideProgress()
            }

        })
    }

    override fun addProductToCart(id: Int, count: Int, perPack: Int) {
        productsFragmentInteractor.requestAddProductToCart(id, count, perPack, object : OnAddToCartProductsListener {
            override fun onSuccessful() {
            }

            override fun onFailure() {
            }

            override fun onRequestEnded() {

            }
        })
    }

    override fun detach() {
    }

    override fun destroy() {
    }
}