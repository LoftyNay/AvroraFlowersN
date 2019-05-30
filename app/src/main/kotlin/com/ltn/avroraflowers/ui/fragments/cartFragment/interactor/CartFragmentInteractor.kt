package com.ltn.avroraflowers.ui.fragments.cartFragment.interactor

import com.ltn.avroraflowers.model.Repository.CartProductsRepository
import com.ltn.avroraflowers.network.RequestBody.AddOrder
import com.ltn.avroraflowers.network.RequestBody.ProductInOrder
import com.ltn.avroraflowers.ui.base.BaseInteractor
import com.ltn.avroraflowers.utils.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CartFragmentInteractor : BaseInteractor(), ICartFragmentInteractor {

    override fun requestDeleteProductsFromCart(onDeleteCartProductsListener: OnDeleteCartProductsListener) {
        disposable = Observable.fromIterable(CartProductsRepository.getInstance().getList())
            .flatMap { i -> apiAvrora.deleteProductInCart(Constants.TEST_TOKEN, i._id) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onDeleteCartProductsListener.onRequestStart() }
            .doFinally { onDeleteCartProductsListener.onRequestEnded() }
            .doOnComplete { onDeleteCartProductsListener.onSuccessful() }
            .subscribe({
                disposable.dispose()
            }, {
                disposable.dispose()
                onDeleteCartProductsListener.onFailure(it)
            })
    }

    override fun requestCartProducts(onRequestCartProductsListener: OnRequestCartProductsListener) {
        disposable = apiAvrora.getProductsInCart(Constants.TEST_TOKEN)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRequestCartProductsListener.onRequestStart() }
            .doFinally { onRequestCartProductsListener.onRequestEnded() }
            .doOnError { onRequestCartProductsListener.onFailure(it) }
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
            .subscribe({
                onDeleteCartProductsListener.onSuccessful()
                disposable.dispose()
            }, {
                onDeleteCartProductsListener.onFailure(it)
                disposable.dispose()
            })
    }

    override fun requestSendOrder(onSendOrderListener: OnSendOrderListener) {
        disposable = apiAvrora.addOrder(Constants.TEST_TOKEN, AddOrder("", "На обработке"))
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
            .doOnError { onSendOrderListener.onFailure(it) }
            .doFinally { onSendOrderListener.onRequestEnded() }
            .doOnComplete { onSendOrderListener.onSuccessful() }
            .subscribe({
                disposable.dispose()
            }, {
                disposable.dispose()
            })
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