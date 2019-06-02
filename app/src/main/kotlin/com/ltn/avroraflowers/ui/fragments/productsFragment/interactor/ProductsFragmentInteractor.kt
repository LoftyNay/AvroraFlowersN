package com.ltn.avroraflowers.ui.fragments.productsFragment.interactor

import android.util.Log
import com.ltn.avroraflowers.model.Repository.CartProductsRepository
import com.ltn.avroraflowers.network.RequestBody.AddToCart
import com.ltn.avroraflowers.ui.base.BaseInteractor
import com.ltn.avroraflowers.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProductsFragmentInteractor : BaseInteractor(), IProductsFragmentInteractor {

    override fun requestProductsByCategoryId(id: Int, onRequestProductsListener: OnRequestProductsListener) {
        disposable = apiAvrora.getProductsByCategoryId(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRequestProductsListener.onRequestStart() }
            .doFinally { onRequestProductsListener.onRequestEnded() }
            .subscribe(
                { result ->
                    onRequestProductsListener.onSuccessful(result)
                    disposable.dispose()
                },
                {
                    onRequestProductsListener.onFailure(it)
                    disposable.dispose()
                }
            )
    }

    override fun requestAddProductToCart(
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
            .subscribe({
                onAddToCartProductsListener.onSuccessful()
                disposable.dispose()
            }, {
                onAddToCartProductsListener.onFailure(it)
                disposable.dispose()
            })
    }
}