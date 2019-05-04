package com.ltn.avroraflowers.ui.fragments.cartFragment.presenter

interface ICartFragmentPresenter {
    fun sendOrder()
    fun deleteProductsFromCart(listIds: MutableList<Int>)
}