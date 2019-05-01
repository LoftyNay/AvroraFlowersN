package com.ltn.avroraflowers.ui.fragments.productsFragment.presenter

interface IProductsFragmentPresenter {
    fun addProductToCart(id: Int, count: Int, perPack: Int)
    fun getProductsFromServerByCategoryId(id: Int)
}