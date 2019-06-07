package com.ltn.avroraflowers.ui.mainFragment.interactor

import com.ltn.avroraflowers.ui.base.BaseInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainFragmentInteractor : BaseInteractor() {

    fun requestLoadLastOrderInCart(onLoadLastOrderInCart: OnLoadLastOrderInCart) {
        disposable = apiAvrora.getLastOrderAndSetToCart(preferencesUtils.getToken()!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onLoadLastOrderInCart.onRequestStart() }
            .doFinally { onLoadLastOrderInCart.onRequestEnded() }
            .subscribe(
                { result ->
                    onLoadLastOrderInCart.onSuccessful()
                    disposable.dispose()
                },
                {
                    onLoadLastOrderInCart.onFailure(it)
                    disposable.dispose()
                }
            )
    }
}