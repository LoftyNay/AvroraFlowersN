package com.ltn.avroraflowers.ui.fragments.searchFragment.presenter

import android.annotation.SuppressLint
import android.os.Handler
import android.util.Log
import com.ltn.avroraflowers.model.Product
import com.ltn.avroraflowers.model.Repository.CartProductsRepository
import com.ltn.avroraflowers.network.RequestBody.AddToCart
import com.ltn.avroraflowers.ui.base.BaseInteractor
import com.ltn.avroraflowers.ui.fragments.productsFragment.interactor.OnAddToCartProductsListener
import com.ltn.avroraflowers.ui.fragments.searchFragment.interactor.OnSearchListener
import com.ltn.avroraflowers.ui.fragments.searchFragment.interactor.SearchFragmentInteractor
import com.ltn.avroraflowers.ui.fragments.searchFragment.view.SearchFragmentView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchFragmentPresenter(private val view: SearchFragmentView) {

    private var searchFragmentInteractor = SearchFragmentInteractor()
    private var products: List<Product>? = null

    fun search(request: String) {
        if (products == null) {
            searchFragmentInteractor.getProducts(object : OnSearchListener {
                override fun onSuccessful(result: List<Product>) {
                    if (result.isEmpty()) {
                        view.showEmpty()
                    } else {
                        products = result
                        searchProducts(request, products!!)
                    }
                }

                override fun onFailure(throwable: Throwable) {
                    view.showConnectionProblem()
                }

                override fun onRequestEnded() {
                    view.hideProgress()
                }

                override fun onRequestStart() {
                    view.showProgress()
                }
            })
        } else
            searchProducts(request, products!!)
    }

    @SuppressLint("CheckResult")
    private fun searchProducts(request: String, products: List<Product>) {
        Observable.fromIterable(products)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .filter { product -> product.title.toLowerCase().contains(request.trim().toLowerCase()) }
            .toList()
            .subscribe({ result ->
                view.showResults(result)
            }, {
                view.showConnectionProblem()
            })
    }

    fun addToCart(
        id: Int,
        count: Int,
        perPack: Int
    ) {
        searchFragmentInteractor.addToCart(id, count, perPack, object : OnAddToCartProductsListener {
            override fun onSuccessful() {
                view.resultOk("Добавлен в корзину")
                CartProductsRepository.getInstance().callUpdate()
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
}