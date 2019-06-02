package com.ltn.avroraflowers.ui.fragments.innerProductFragment.interactor

import android.util.Log
import com.ltn.avroraflowers.network.RequestBody.AddToCart
import com.ltn.avroraflowers.ui.base.BaseInteractor
import com.ltn.avroraflowers.ui.fragments.productsFragment.interactor.OnAddToCartProductsListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class InnerProductFragmentInteractor : BaseInteractor(), IInnerProductFragmentInteractor {

    override fun requestProductFromServer(id: Int, onRequestProductListener: OnRequestProductListener) {
        disposable = apiAvrora.getProductById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRequestProductListener.onRequestStart() }
            .doFinally { onRequestProductListener.onRequestEnded() }
            .subscribe(
                { result ->
                    onRequestProductListener.onSuccessful(result)
                    disposable.dispose()
                },
                {
                    onRequestProductListener.onFailure(it)
                    disposable.dispose()
                }
            )
    }

    fun requestAddToCart(id: Int, count: Int, perPack: Int, onAddToCartProductsListener: OnAddToCartProductsListener) {
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