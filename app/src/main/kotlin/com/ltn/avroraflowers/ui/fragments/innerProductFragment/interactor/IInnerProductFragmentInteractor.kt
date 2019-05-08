package com.ltn.avroraflowers.ui.fragments.innerProductFragment.interactor

interface IInnerProductFragmentInteractor {
    fun requestProductFromServer(id: Int, onRequestProductListener: OnRequestProductListener)
}