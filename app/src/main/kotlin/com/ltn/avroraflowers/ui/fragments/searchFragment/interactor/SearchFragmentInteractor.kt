package com.ltn.avroraflowers.ui.fragments.searchFragment.interactor

import android.util.Log
import com.ltn.avroraflowers.network.RequestBody.AddToCart
import com.ltn.avroraflowers.ui.base.BaseInteractor
import com.ltn.avroraflowers.ui.fragments.productsFragment.interactor.OnAddToCartProductsListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchFragmentInteractor : BaseInteractor() {

    fun getProducts(onSearchListener: OnSearchListener) {
        disposable = apiAvrora.getProducts(1000)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry(4)
            .doOnSubscribe { onSearchListener.onRequestStart() }
            .doFinally { onSearchListener.onRequestEnded() }
            .subscribe(
                { res ->
                    onSearchListener.onSuccessful(res.result)
                    disposable.dispose()
                },
                {
                    onSearchListener.onFailure(it)
                    disposable.dispose()
                }
            )
    }

    fun addToCart(
        id: Int,
        count: Int,
        perPack: Int,
        onAddToCartProductsListener: OnAddToCartProductsListener
    ) {
        val addToCardBody = AddToCart(id, count, perPack)
        disposable = apiAvrora.addProductInCart(preferencesUtils.getToken()!!, addToCardBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onAddToCartProductsListener.onRequestStart() }
            .doFinally { onAddToCartProductsListener.onRequestEnded() }
            .subscribe({}, {
                onAddToCartProductsListener.onFailure(it)
            })
    }
}