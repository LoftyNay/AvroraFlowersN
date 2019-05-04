package com.ltn.avroraflowers.model.Repository

import com.ltn.avroraflowers.model.OrderItem

abstract class BaseRepository<T> {

    private var list: MutableList<T> = ArrayList()

    fun addAllItems(list: List<T>) {
        this.list.clear()
        this.list.addAll(list)
    }

    fun getList(): MutableList<T> {
        return this.list
    }

    abstract fun callUpdate()
}