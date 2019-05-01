package com.ltn.avroraflowers.ui.fragments.cartFragment.interactor

import android.util.Log
import com.ltn.avroraflowers.ui.base.BaseInteractor
import com.ltn.avroraflowers.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CartFragmentInteractor: BaseInteractor(), ICartFragmentInteractor {

    override fun requestCartProducts(onRequestCartProductsListener: OnRequestCartProductsListener) {
        disposable = apiAvrora.getProductsInCart(Constants.TEST_TOKEN)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    disposable.dispose()
                    onRequestCartProductsListener.onSuccessful(result)
                    onRequestCartProductsListener.onRequestEnded()
                },
                { error ->
                    disposable.dispose()
                    onRequestCartProductsListener.onFailure()
                    onRequestCartProductsListener.onRequestEnded()
                }
            )
    }
}