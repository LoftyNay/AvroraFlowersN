package com.ltn.avroraflowers.ui.fragments.cartFragment.interactor

import android.util.Log
import com.ltn.avroraflowers.model.CartItem
import com.ltn.avroraflowers.model.Repository.CartProductsRepository
import com.ltn.avroraflowers.network.RequestBody.AddOrder
import com.ltn.avroraflowers.network.RequestBody.ProductInOrder
import com.ltn.avroraflowers.network.RequestBody.SaveCartName
import com.ltn.avroraflowers.network.Response.SampleResponse
import com.ltn.avroraflowers.ui.base.BaseInteractor
import com.ltn.avroraflowers.utils.Constants
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class CartFragmentInteractor : BaseInteractor(), ICartFragmentInteractor {


    fun requestSaveCart(name: String, onSaveCartListener: OnSaveCartListener) {
        disposable = apiAvrora.saveCart(preferencesUtils.getToken()!!, SaveCartName(name))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onSaveCartListener.onRequestStart() }
            .doFinally { onSaveCartListener.onRequestEnded() }
            .subscribe(
                { result ->
                    onSaveCartListener.onSuccessful()
                    disposable.dispose()
                },
                {
                    onSaveCartListener.onFailure(it)
                    disposable.dispose()
                }
            )
    }

    override fun requestDeleteProductsFromCart(onDeleteCartProductsListener: OnDeleteCartProductsListener) {
        disposable = apiAvrora.deleteAllProductsInCart(preferencesUtils.getToken()!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onDeleteCartProductsListener.onRequestStart() }
            .doFinally { onDeleteCartProductsListener.onRequestEnded() }
            .subscribe(
                { result ->
                    onDeleteCartProductsListener.onSuccessful()
                    disposable.dispose()
                },
                {
                    onDeleteCartProductsListener.onFailure(it)
                    disposable.dispose()
                }
            )
    }

    override fun requestDeleteProductsFromCart(
        listIds: MutableList<Int>,
        onDeleteCartProductsListener: OnDeleteCartProductsListener
    ) {
        disposable = Observable.fromIterable(listIds)
            .flatMap { i -> apiAvrora.deleteProductInCart(preferencesUtils.getToken()!!, i) }
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

    override fun requestCartProducts(onRequestCartProductsListener: OnRequestCartProductsListener) {
        disposable = apiAvrora.getProductsInCart(preferencesUtils.getToken()!!)
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

    override fun requestSendOrder(onSendOrderListener: OnSendOrderListener) {
        disposable = apiAvrora.addOrder(preferencesUtils.getToken()!!, AddOrder("", "На обработке"))
            .flatMap {
                Observable.fromIterable(CartProductsRepository.getInstance().getList()).flatMap {
                    apiAvrora.addProductsInOrder(
                        preferencesUtils.getToken()!!,
                        ProductInOrder(it.count_pack, it.per_pack, it.price, it.product_id)
                    )
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { onSendOrderListener.onRequestStart() }
            .doFinally { onSendOrderListener.onRequestEnded() }
            .subscribe({
                onSendOrderListener.onSuccessful()
            }, {
                onSendOrderListener.onFailure(it)
            })
    }
}