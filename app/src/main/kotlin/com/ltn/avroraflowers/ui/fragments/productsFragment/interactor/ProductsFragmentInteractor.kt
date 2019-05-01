package com.ltn.avroraflowers.ui.fragments.productsFragment.interactor

import android.util.Log
import com.ltn.avroraflowers.network.RequestBody.AddToCart
import com.ltn.avroraflowers.ui.base.BaseInteractor
import com.ltn.avroraflowers.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProductsFragmentInteractor : BaseInteractor(), IProductsFragmentInteractor {

    override fun requestProductsByCategoryId(id: Int, onRequestProductsListener: OnRequestProductsListener) {
        disposable = apiAvrora.getProductsByCategoryId(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    disposable.dispose()
                    onRequestProductsListener.onSuccessful(result)
                    onRequestProductsListener.onRequestEnded()
                },
                { error ->
                    disposable.dispose()
                    onRequestProductsListener.onFailure()
                    onRequestProductsListener.onRequestEnded()
                }
            )

    }

    override fun requestAddProductToCart(
        id: Int,
        count: Int,
        perPack: Int,
        onAddToCartProductsListener: OnAddToCartProductsListener
    ) {
        val addToCardBody = AddToCart(id, count, perPack)
        disposable = apiAvrora.addProductInCart(Constants.TEST_TOKEN, addToCardBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    disposable.dispose()
                    onAddToCartProductsListener.onSuccessful()
                    onAddToCartProductsListener.onRequestEnded()
                    Log.d("GLL", "ok")
                },
                { error ->
                    disposable.dispose()
                    onAddToCartProductsListener.onFailure()
                    onAddToCartProductsListener.onRequestEnded()
                    Log.d("GLL", "fail")
                }
            )
    }
}