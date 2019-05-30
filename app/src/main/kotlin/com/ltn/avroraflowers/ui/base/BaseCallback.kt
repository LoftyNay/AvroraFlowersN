package com.ltn.avroraflowers.ui.base

interface BaseCallback {
    fun onFailure(throwable: Throwable)
    fun onRequestEnded()
    fun onRequestStart()
}