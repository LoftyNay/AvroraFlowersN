package com.ltn.avroraflowers.ui.fragments.cartFragment.interactor

import android.util.Log
import com.ltn.avroraflowers.model.Repository.CartProductsRepository
import com.ltn.avroraflowers.network.RequestBody.AddOrder
import com.ltn.avroraflowers.network.RequestBody.ProductInOrder
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
            .subscribe({}, {
                onDeleteCartProductsListener.onFailure()
            })
    }

    override fun requestSendOrder(onSendOrderListener: OnSendOrderListener) {
        disposable = apiAvrora.addOrder(Constants.TEST_TOKEN, AddOrder("10$", "На обработке"))
            .flatMap {
                Observable.fromIterable(CartProductsRepository.getInstance().getList()).flatMap {
                    apiAvrora.addProductsInOrder(
                        Constants.TEST_TOKEN,
                        ProductInOrder(it.count_pack, it.per_pack, it.price, it.product_id)
                    )
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { onSendOrderListener.onRequestStart() }
            .doOnError { onSendOrderListener.onFailure() }
            .doFinally { onSendOrderListener.onRequestEnded() }
            .doOnComplete { onSendOrderListener.onSuccessful() }
            .subscribe()
    }
}


/*
just(AddOrder("10$", "Упаковка"))
.flatMap { order -> apiAvrora.addOrder(Constants.TEST_TOKEN, order) }
.flatMap { apiAvrora.addProductsInOrder(Constants.TEST_TOKEN, ) }
//disposable = Observable.fromIterable(CartProductsRepository.getInstance().getList())
//  .
// disposable =

}
}*/