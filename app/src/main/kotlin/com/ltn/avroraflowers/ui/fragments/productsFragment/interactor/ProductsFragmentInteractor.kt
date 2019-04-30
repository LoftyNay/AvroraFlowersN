package com.ltn.avroraflowers.ui.fragments.productsFragment.interactor

import com.ltn.avroraflowers.ui.base.BaseInteractor
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

    override fun requestAddProductToCartByUserToken(accessToken: String, onAddToCartProductsListener: OnAddToCartProductsListener) {

    }


}