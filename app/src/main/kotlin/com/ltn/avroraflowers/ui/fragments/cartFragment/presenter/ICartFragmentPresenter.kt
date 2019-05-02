package com.ltn.avroraflowers.ui.fragments.cartFragment.presenter

interface ICartFragmentPresenter {
    fun getCartProducts()
    fun deleteProductsFromCart(listIds: MutableList<Int>)
}