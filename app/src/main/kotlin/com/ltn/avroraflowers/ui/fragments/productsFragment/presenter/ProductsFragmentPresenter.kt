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
        productsFragmentInteractor.requestProductsByCategoryId(id, object: OnRequestProductsListener {
            override fun onSuccessful(products: List<Product>) {
                viewState.showProducts(products)
            }

            override fun onFailure() {
                Log.d("GLL", "fail")
            }

            override fun onRequestEnded() {
            }

        })
    }

    override fun addProductToCartByUserToken(token: String) {
        productsFragmentInteractor.requestAddProductToCartByUserToken(token, object: OnAddToCartProductsListener {
            override fun onSuccessful(productName: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onFailure() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onRequestEnded() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    override fun detach() {

    }

    override fun destroy() {

    }
}