package com.ltn.avroraflowers.ui.fragments.innerOrderFragment.interactor

interface IInnerOrderFragmentInteractor {
    fun requestOrderInfoFromServer(id:Int, onRequestOrderInfoListener: OnRequestOrderInfoListener)
}