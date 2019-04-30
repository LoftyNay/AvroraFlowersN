package com.ltn.avroraflowers.ui.fragments.productsFragment.interactor

interface IProductsFragmentInteractor {
    fun requestAddProductToCartByUserToken(accessToken: String, onAddToCartProductsListener: OnAddToCartProductsListener)
    fun requestProductsByCategoryId(id: Int, onRequestProductsListener: OnRequestProductsListener)
}