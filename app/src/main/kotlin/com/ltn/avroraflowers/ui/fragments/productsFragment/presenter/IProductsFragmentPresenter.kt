package com.ltn.avroraflowers.ui.fragments.productsFragment.presenter

interface IProductsFragmentPresenter {
    fun getProductsFromServerByCategoryId(id: Int)
    fun addProductToCartByUserToken(token: String)
}