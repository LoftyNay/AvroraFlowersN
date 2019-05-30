package com.ltn.avroraflowers.ui.fragments.searchFragment.interactor

import android.util.Log
import com.ltn.avroraflowers.ui.base.BaseInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchFragmentInteractor: BaseInteractor() {

    fun getProducts(onSearchListener: OnSearchListener) {
        disposable = apiAvrora.getProducts(1000)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onSearchListener.onRequestStart() }
            .doFinally { onSearchListener.onRequestEnded() }
            .subscribe(
                { res ->
                    onSearchListener.onSuccessful(res.result)
                    disposable.dispose()
                },
                {
                    onSearchListener.onFailure(it)
                    disposable.dispose()
                }
            )
    }

}