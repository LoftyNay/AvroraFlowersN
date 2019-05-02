package com.ltn.avroraflowers.ui.fragments.cartFragment.interactor

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log
import com.ltn.avroraflowers.App
import com.ltn.avroraflowers.network.ApiAvrora
import com.ltn.avroraflowers.utils.Constants
import io.reactivex.disposables.Disposable
import javax.inject.Inject


class DeleteProductsFromCartService : IntentService("DeleteProductsFromCartService") {

    @Inject
    lateinit var apiAvrora: ApiAvrora

    init {
        App.component.inject(this)
    }

    lateinit var disposable: Disposable

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, DeleteProductsFromCartService::class.java)
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        val list = intent?.getIntegerArrayListExtra("ids")
        for (item in list!!.iterator()) {
            delete(item)
        }
    }

    private fun delete(id: Int) {

        //fixme
        disposable = apiAvrora.deleteProductInCart(Constants.TEST_TOKEN, id)
            .subscribe(
                { result ->
                    disposable.dispose()
                    Log.d("GLL", "ok")
                },
                { error ->
                    Log.d("GLL", error.toString())
                    disposable.dispose()
                }
            )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}