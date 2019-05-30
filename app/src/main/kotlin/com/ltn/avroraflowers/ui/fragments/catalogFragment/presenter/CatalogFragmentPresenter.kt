package com.ltn.avroraflowers.ui.fragments.catalogFragment.presenter

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.ltn.avroraflowers.App
import com.ltn.avroraflowers.model.Category
import com.ltn.avroraflowers.ui.base.BasePresenter
import com.ltn.avroraflowers.ui.fragments.catalogFragment.interactor.CatalogFragmentInteractor
import com.ltn.avroraflowers.ui.fragments.catalogFragment.interactor.OnRequestCategoriesListener
import com.ltn.avroraflowers.ui.fragments.catalogFragment.view.CatalogFragmentView
import javax.inject.Inject

@InjectViewState
class CatalogFragmentPresenter : BasePresenter<CatalogFragmentView>(), ICatalogFragmentPresenter,
    OnRequestCategoriesListener {

    @Inject
    lateinit var catalogFragmentInteractor: CatalogFragmentInteractor

    override fun attach() {
        App.component.inject(this)
    }

    override fun detach() {
    }

    override fun destroy() {
    }

    override fun loadCategories() {
        catalogFragmentInteractor.requestCategoriesFromServer(this)
    }

    override fun onRequestStart() {
        viewState.showProgress()
    }

    override fun onSuccessful(categories: List<Category>) {
        viewState.showCategoriesList(categories)
    }

    override fun onFailure(throwable: Throwable) {
        viewState.showConnectionProblem()
    }

    override fun onRequestEnded() {
        viewState.hideProgress()
    }
}