package com.ltn.avroraflowers.ui.fragments.ordersFragment.presenter

import android.content.Context
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.ltn.avroraflowers.App
import com.ltn.avroraflowers.model.OrderItem
import com.ltn.avroraflowers.model.Repository.OrdersRepository
import com.ltn.avroraflowers.ui.base.BasePresenter
import com.ltn.avroraflowers.ui.fragments.ordersFragment.interactor.OnRequestOrdersListener
import com.ltn.avroraflowers.ui.fragments.ordersFragment.interactor.OrdersFragmentInteractor
import com.ltn.avroraflowers.ui.fragments.ordersFragment.view.OrdersFragmentView
import javax.inject.Inject

@InjectViewState
class OrdersFragmentPresenter: BasePresenter<OrdersFragmentView>(), IOrdersFragmentPresenter, OrdersRepository.OnOrdersListener {

    @Inject
    lateinit var ordersFragmentInteractor: OrdersFragmentInteractor

    override fun attach() {
        App.component.inject(this)
        OrdersRepository.getInstance().registerListener(this)
    }

    override fun onUpdateOrders() {
        ordersFragmentInteractor.requestOrdersFromServer(object : OnRequestOrdersListener {
            override fun onRequestStart() {
                viewState.showProgress()
            }

            override fun onSuccessful(orders: List<OrderItem>) {
                OrdersRepository.getInstance().addAllItems(orders)
                viewState.invalidateRecycler()
            }

            override fun onFailure() {
            }

            override fun onRequestEnded() {
                viewState.hideProgress()
            }
        })
    }

    override fun getOrders() {

    }

    override fun detach() {
    }

    override fun destroy() {
        OrdersRepository.getInstance().unregisterListener(this)
    }
}