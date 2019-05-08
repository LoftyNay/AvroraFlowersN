package com.ltn.avroraflowers.ui.fragments.innerProductFragment.interactor

import android.util.Log
import com.ltn.avroraflowers.ui.base.BaseInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class InnerProductFragmentInteractor: BaseInteractor(), IInnerProductFragmentInteractor {

    override fun requestProductFromServer(id: Int, onRequestProductListener: OnRequestProductListener) {
        disposable = apiAvrora.getProductById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRequestProductListener.onRequestStart() }
            .doFinally { onRequestProductListener.onRequestEnded() }
            .doOnError { onRequestProductListener.onFailure() }
            .subscribe(
                { result ->
                    onRequestProductListener.onSuccessful(result)
                    disposable.dispose()
                },
                {
                    disposable.dispose()
                    Log.d("GLL", it.message.toString())
                }
            )
    }
}