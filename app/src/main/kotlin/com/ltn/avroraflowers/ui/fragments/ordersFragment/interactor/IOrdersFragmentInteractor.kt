package com.ltn.avroraflowers.ui.fragments.ordersFragment.interactor

interface IOrdersFragmentInteractor {
    fun requestOrdersFromServer(onRequestOrdersListener: OnRequestOrdersListener)
}