package com.ltn.avroraflowers.ui.fragments.catalogFragment.interactor

import com.ltn.avroraflowers.model.Category
import com.ltn.avroraflowers.network.Response.ResponseCategory

interface OnRequestCategoriesListener {
    fun onSuccessful(categories: List<Category>)
    fun onFailure()
    fun onRequestEnded()
}