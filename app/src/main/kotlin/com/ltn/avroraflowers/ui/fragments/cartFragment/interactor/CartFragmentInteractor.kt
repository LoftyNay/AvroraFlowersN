package com.ltn.avroraflowers.ui.fragments.cartFragment.interactor

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.ltn.avroraflowers.ui.base.BaseInteractor
import com.ltn.avroraflowers.utils.Constants
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class CartFragmentInteractor : BaseInteractor(), ICartFragmentInteractor {

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
/*
    override fun requestDeleteProductsFromCart(listIds: MutableList<Int>) {
        val workManager = WorkManager.getInstance()
        lateinit var task: OneTimeWorkRequest
        for (id in listIds) {
            task = OneTimeWorkRequest.Builder(DeleteProductsFromCartWorker::class.java)
                .setInputData(Data.Builder().putInt(DeleteProductsFromCartWorker.ID_KEY, id).build())
                .build()
            workManager.enqueue(task)
        }
    }*/
}