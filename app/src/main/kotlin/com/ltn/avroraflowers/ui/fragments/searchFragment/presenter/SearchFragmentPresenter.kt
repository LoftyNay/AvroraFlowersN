package com.ltn.avroraflowers.ui.fragments.searchFragment.presenter

import android.annotation.SuppressLint
import android.util.Log
import com.ltn.avroraflowers.model.Product
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
            .filter { product -> product.title == request }
            .toList()
            .subscribe({ result ->
                Log.d("GLL", "Dsdsds")
                view.showResults(result)
            }, {
                view.showConnectionProblem()
            })
    }
}