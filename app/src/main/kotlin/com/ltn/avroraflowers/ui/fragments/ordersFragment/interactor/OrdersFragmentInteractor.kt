package com.ltn.avroraflowers.ui.fragments.ordersFragment.interactor

import com.ltn.avroraflowers.ui.base.BaseInteractor
import com.ltn.avroraflowers.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class OrdersFragmentInteractor : BaseInteractor(), IOrdersFragmentInteractor {

    override fun requestOrdersFromServer(onRequestOrdersListener: OnRequestOrdersListener) {
        disposable = apiAvrora.getOrders(Constants.TEST_TOKEN)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRequestOrdersListener.onRequestStart() }
            .doFinally { onRequestOrdersListener.onRequestEnded() }
            .doOnError { onRequestOrdersListener.onFailure() }
            .subscribe(
                { result ->
                    onRequestOrdersListener.onSuccessful(result)
                    disposable.dispose()
                },
                {
                    disposable.dispose()
                }
            )
    }
}