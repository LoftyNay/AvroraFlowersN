package com.ltn.avroraflowers.ui.fragments.innerOrderFragment.interactor

import com.ltn.avroraflowers.ui.base.BaseInteractor
import com.ltn.avroraflowers.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class InnerOrderFragmentInteractor : BaseInteractor(), IInnerOrderFragmentInteractor {

    override fun requestOrderInfoFromServer(id: Int, onRequestOrderInfoListener: OnRequestOrderInfoListener) {
        disposable = apiAvrora.getProductsInOrderById(preferencesUtils.getToken()!!, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRequestOrderInfoListener.onRequestStart() }
            .doFinally { onRequestOrderInfoListener.onRequestEnded() }
            .subscribe(
                { result ->
                    onRequestOrderInfoListener.onSuccessful(result)
                    disposable.dispose()
                },
                {
                    onRequestOrderInfoListener.onFailure(it)
                    disposable.dispose()
                }
            )
    }
}