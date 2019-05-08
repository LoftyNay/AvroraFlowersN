package com.ltn.avroraflowers.model.Repository

import com.ltn.avroraflowers.model.OrderItem

class OrdersRepository : BaseRepository<OrderItem, OrdersRepository.OnOrdersListener>() {

    companion object {
        private var ordersRepository: OrdersRepository? = null
        fun getInstance(): OrdersRepository {
            if (ordersRepository == null)
                ordersRepository = OrdersRepository()
            return ordersRepository as OrdersRepository
        }
    }

    override fun callUpdate() {
        listeners.forEach {
            it.onUpdateOrders()
        }
    }

    interface OnOrdersListener {
        fun onUpdateOrders()
    }
}