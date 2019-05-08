package com.ltn.avroraflowers.model.Repository

import android.util.Log
import com.ltn.avroraflowers.model.OrderItem

abstract class BaseRepository<T1, T2> {

    protected var listeners: MutableList<T2> = ArrayList()
    private var list: MutableList<T1> = ArrayList()

    fun addAllItems(list: List<T1>) {
        this.list.clear()
        this.list.addAll(list)
    }

    fun getList(): MutableList<T1> {
        return this.list
    }


    fun registerListener(listener: T2) {
        if (!listeners.contains(listener)) {
            listeners.add(listener)
        }
        Log.d("GLL", listeners.size.toString())
    }

    fun unregisterListener(listener: T2) {
        if (listeners.contains(listener)) {
            listeners.removeAt(listeners.indexOf(listener))
        }
        Log.d("GLL", listeners.size.toString())
    }

    abstract fun callUpdate()
}