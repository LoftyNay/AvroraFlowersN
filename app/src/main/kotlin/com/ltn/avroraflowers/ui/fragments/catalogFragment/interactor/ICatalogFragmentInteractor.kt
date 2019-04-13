package com.ltn.avroraflowers.ui.fragments.catalogFragment.interactor

interface ICatalogFragmentInteractor {
    fun requestCategoriesFromServer(onRequestCategoriesListener: OnRequestCategoriesListener)
}