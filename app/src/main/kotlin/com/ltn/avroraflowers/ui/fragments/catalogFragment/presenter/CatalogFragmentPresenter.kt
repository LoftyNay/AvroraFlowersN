package com.ltn.avroraflowers.ui.fragments.catalogFragment.presenter

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.ltn.avroraflowers.App
import com.ltn.avroraflowers.model.Category
import com.ltn.avroraflowers.network.Response.ResponseCategory
import com.ltn.avroraflowers.ui.base.BasePresenter
import com.ltn.avroraflowers.ui.fragments.catalogFragment.interactor.CatalogFragmentInteractor
import com.ltn.avroraflowers.ui.fragments.catalogFragment.interactor.OnRequestCategoriesListener
import com.ltn.avroraflowers.ui.fragments.catalogFragment.view.CatalogFragmentView
import javax.inject.Inject

@InjectViewState
class CatalogFragmentPresenter: BasePresenter<CatalogFragmentView>(), ICatalogFragmentPresenter, OnRequestCategoriesListener {

    override fun attach(context: Context) {
        App.component.inject(this)
    }

    @Inject
    lateinit var catalogFragmentInteractor: CatalogFragmentInteractor

    override fun detach() {
    }

    override fun destroy() {
    }

    override fun loadCategories() {
        catalogFragmentInteractor.requestCategoriesFromServer(this)
    }

    override fun onSuccessful(categories: List<Category>) {
        viewState.showCategoriesList(categories)
    }

    override fun onFailure() {
    }

    override fun onRequestEnded() {
    }
}