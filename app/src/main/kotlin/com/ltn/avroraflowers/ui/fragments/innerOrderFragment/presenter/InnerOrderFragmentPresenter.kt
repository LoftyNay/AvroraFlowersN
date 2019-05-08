package com.ltn.avroraflowers.ui.fragments.innerOrderFragment.presenter

import com.arellomobile.mvp.InjectViewState
import com.ltn.avroraflowers.App
import com.ltn.avroraflowers.model.OrderInner
import com.ltn.avroraflowers.ui.base.BasePresenter
import com.ltn.avroraflowers.ui.fragments.innerOrderFragment.interactor.InnerOrderFragmentInteractor
import com.ltn.avroraflowers.ui.fragments.innerOrderFragment.interactor.OnRequestOrderInfoListener
import com.ltn.avroraflowers.ui.fragments.innerOrderFragment.view.InnerOrderFragmentView
import javax.inject.Inject

@InjectViewState
class InnerOrderFragmentPresenter: BasePresenter<InnerOrderFragmentView>(), IInnerOrderFragmentPresenter {

    @Inject
    lateinit var innerOrderFragmentInteractor: InnerOrderFragmentInteractor

    override fun attach() {
        App.component.inject(this)
    }

    override fun getOrderInfo(id: Int) {
        innerOrderFragmentInteractor.requestOrderInfoFromServer(id, object : OnRequestOrderInfoListener {
            override fun onRequestStart() {
                viewState.showProgress()
            }
            override fun onSuccessful(list: List<OrderInner>) {
                viewState.showProductsInOrder(list)
            }

            override fun onFailure() {
                viewState.showConnectionProblem()
            }

            override fun onRequestEnded() {
                viewState.hideProgress()
            }
        })
    }

    override fun detach() {
    }

    override fun destroy() {
    }
}