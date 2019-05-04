package com.ltn.avroraflowers.ui.fragments.cartFragment.interactor

import com.ltn.avroraflowers.ui.base.BaseInteractor
import com.ltn.avroraflowers.utils.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CartFragmentInteractor : BaseInteractor(), ICartFragmentInteractor {

    override fun requestCartProducts(onRequestCartProductsListener: OnRequestCartProductsListener) {
        disposable = apiAvrora.getProductsInCart(Constants.TEST_TOKEN)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRequestCartProductsListener.onRequestStart() }
            .doFinally { onRequestCartProductsListener.onRequestEnded() }
            .doOnError { onRequestCartProductsListener.onFailure() }
            .subscribe(
                { result ->
                    onRequestCartProductsListener.onSuccessful(result.sortedWith(compareByDescending({ it._id })))
                    disposable.dispose()

                },
                {
                    disposable.dispose()
                }
            )
    }

    override fun requestDeleteProductsFromCart(
        listIds: MutableList<Int>,
        onDeleteCartProductsListener: OnDeleteCartProductsListener
    ) {
        disposable = Observable.fromIterable(listIds)
            .flatMap { i -> apiAvrora.deleteProductInCart(Constants.TEST_TOKEN, i) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onDeleteCartProductsListener.onRequestStart() }
            .doFinally { onDeleteCartProductsListener.onRequestEnded() }
            .doOnComplete { onDeleteCartProductsListener.onSuccessful() }
            .subscribe({},{ error ->
                onDeleteCartProductsListener.onFailure()
            })
    }

    override fun requestSendOrder(onSendOrderListener: OnSendOrderListener) {
       // disposable =

    }
}