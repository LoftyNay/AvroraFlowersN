package com.ltn.avroraflowers.ui.fragments.productsFragment.interactor

interface IProductsFragmentInteractor {
    fun requestProductsByCategoryId(id: Int, onRequestProductsListener: OnRequestProductsListener)
    fun requestAddProductToCart(id: Int, count: Int, perPack: Int, onAddToCartProductsListener: OnAddToCartProductsListener)
}