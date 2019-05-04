package com.ltn.avroraflowers.model.Repository

import com.ltn.avroraflowers.model.OrderItem

class OrdersRepository : BaseRepository<OrderItem>() {

    private var onUpdateOrdersListener: OnUpdateOrdersListener? = null

    companion object {
        private var ordersRepository: OrdersRepository? = null
        fun getInstance(): OrdersRepository {
            if (ordersRepository == null)
                ordersRepository = OrdersRepository()
            return ordersRepository as OrdersRepository
        }
    }

    fun registerListener(onUpdateOrdersListener: OnUpdateOrdersListener) {
        this.onUpdateOrdersListener = onUpdateOrdersListener
    }

    override fun callUpdate() {
        onUpdateOrdersListener?.onUpdateOrders()
    }

    interface OnUpdateOrdersListener {
        fun onUpdateOrders()
    }
}