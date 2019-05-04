package com.ltn.avroraflowers.ui.fragments.cartFragment.interactor

interface ICartFragmentInteractor {
    fun requestCartProducts(onRequestCartProductsListener: OnRequestCartProductsListener)
    fun requestDeleteProductsFromCart(listIds: MutableList<Int>, onDeleteCartProductsListener: OnDeleteCartProductsListener)
    fun requestSendOrder(onSendOrderListener: OnSendOrderListener)
}