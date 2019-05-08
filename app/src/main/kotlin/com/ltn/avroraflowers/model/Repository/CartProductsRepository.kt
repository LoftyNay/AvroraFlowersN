package com.ltn.avroraflowers.model.Repository

import android.util.Log
import com.ltn.avroraflowers.model.CartItem

class CartProductsRepository : BaseRepository<CartItem, CartProductsRepository.OnCartListener>() {

    companion object {
        private var cartProductsRepository: CartProductsRepository? = null
        fun getInstance(): CartProductsRepository {
            if (cartProductsRepository == null)
                cartProductsRepository = CartProductsRepository()
            return cartProductsRepository as CartProductsRepository
        }
    }

    override fun callUpdate() {
        listeners.forEach {
            it.onUpdateProductsInCart()
        }
    }

    interface OnCartListener {
        fun onUpdateProductsInCart()
    }
}