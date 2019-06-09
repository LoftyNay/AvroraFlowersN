package com.ltn.avroraflowers.ui.fragments.selectSavedOrderDIalogFragment.interactor

import android.os.Handler
import com.ltn.avroraflowers.model.SavedProduct
import com.ltn.avroraflowers.model.SavedProductKey
import com.ltn.avroraflowers.network.Response.SavedOrder
import com.ltn.avroraflowers.ui.base.BaseInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SelectSavedOrderDialogInteractor : BaseInteractor() {

    fun requestSavedOrders(onLoadSavedOrdersListener: OnLoadSavedOrdersListener) {
        disposable = apiAvrora.getSavedOrders(preferencesUtils.getToken()!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry(3)
            .doOnSubscribe { onLoadSavedOrdersListener.onRequestStart() }
            .doFinally { onLoadSavedOrdersListener.onRequestEnded() }
            .subscribe(
                { res ->
                    onLoadSavedOrdersListener.onSuccessful(convertSavedOrders(res))
                    disposable.dispose()
                },
                {
                    onLoadSavedOrdersListener.onFailure(it)
                    disposable.dispose()
                }
            )
    }

    private fun convertSavedOrders(savedOrders: List<SavedOrder>): HashMap<SavedProductKey, MutableList<SavedProduct>> {
        val savedOrdersHash: HashMap<SavedProductKey, MutableList<SavedProduct>> = HashMap()
            for (i in savedOrders.indices) {
                if (!savedOrdersHash.containsKey(SavedProductKey(savedOrders[i].save_order_id, savedOrders[i].name))) {
                    val savedProducts: MutableList<SavedProduct> = ArrayList()
                    savedProducts.add(
                        SavedProduct(
                            savedOrders[i].title,
                            savedOrders[i].per_pack,
                            savedOrders[i].count_pack
                        )
                    )
                    savedOrdersHash.put(SavedProductKey(savedOrders[i].save_order_id, savedOrders[i].name), savedProducts)
                } else {
                    savedOrdersHash.get(SavedProductKey(savedOrders[i].save_order_id, savedOrders[i].name))?.add(
                        SavedProduct(
                            savedOrders[i].title, savedOrders[i].per_pack,
                            savedOrders[i].count_pack
                        )
                    )
                }
            }
        return savedOrdersHash
    }

    fun requestAddSavedOrderInCart(orderId: Int, onAddSavedOrderInCart: OnAddSavedOrderInCart) {
        disposable = apiAvrora.getSavedOrderAndSendInCart(preferencesUtils.getToken()!!, orderId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry(3)
            .doOnSubscribe { onAddSavedOrderInCart.onRequestStart() }
            .doFinally { onAddSavedOrderInCart.onRequestEnded() }
            .subscribe(
                { res ->
                    onAddSavedOrderInCart.onSuccessful()
                    disposable.dispose()
                },
                {
                    onAddSavedOrderInCart.onFailure(it)
                    disposable.dispose()
                }
            )
    }
}