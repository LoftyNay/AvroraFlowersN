package com.ltn.avroraflowers.ui.fragments.ordersFragment.interactor

import com.ltn.avroraflowers.ui.base.BaseInteractor
import com.ltn.avroraflowers.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class OrdersFragmentInteractor : BaseInteractor(), IOrdersFragmentInteractor {

    override fun requestOrdersFromServer(onRequestOrdersListener: OnRequestOrdersListener) {
        disposable = apiAvrora.getOrders(preferencesUtils.getToken()!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRequestOrdersListener.onRequestStart() }
            .doFinally { onRequestOrdersListener.onRequestEnded() }
            .subscribe(
                { result ->
                    onRequestOrdersListener.onSuccessful(result.sortedWith(compareByDescending { it._id }))
                    disposable.dispose()
                },
                {
                    onRequestOrdersListener.onFailure(it)
                    disposable.dispose()
                }
            )
    }
}