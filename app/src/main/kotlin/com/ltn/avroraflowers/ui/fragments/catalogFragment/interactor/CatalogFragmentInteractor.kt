package com.ltn.avroraflowers.ui.fragments.catalogFragment.interactor

import android.util.Log
import com.ltn.avroraflowers.ui.base.BaseInteractor
import com.ltn.avroraflowers.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CatalogFragmentInteractor: BaseInteractor(), ICatalogFragmentInteractor {

    override fun requestCategoriesFromServer(onRequestCategoriesListener: OnRequestCategoriesListener) {
        disposable = apiAvrora.getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRequestCategoriesListener.onRequestStart() }
            .doFinally { onRequestCategoriesListener.onRequestEnded() }
            .doOnError { onRequestCategoriesListener.onFailure() }
            .subscribe(
                { result->
                    onRequestCategoriesListener.onSuccessful(result)
                    disposable.dispose()
                },
                {
                    disposable.dispose()
                }
            )
    }
}