package com.ltn.avroraflowers.model.Repository

import com.ltn.avroraflowers.model.CartItem

class CartProductsRepository : BaseRepository<CartItem>() {

    private var onUpdateCartListener: OnUpdateCartListener? = null

    companion object {
        private var cartProductsRepository: CartProductsRepository? = null
        fun getInstance(): CartProductsRepository {
            if (cartProductsRepository == null)
                cartProductsRepository = CartProductsRepository()
            return cartProductsRepository as CartProductsRepository
        }
    }

    fun registerListener(onUpdateCartListener: OnUpdateCartListener) {
        this.onUpdateCartListener = onUpdateCartListener
    }

    override fun callUpdate() {
        onUpdateCartListener?.onUpdateProductsInCart()
    }

    interface OnUpdateCartListener {
        fun onUpdateProductsInCart()
    }
}