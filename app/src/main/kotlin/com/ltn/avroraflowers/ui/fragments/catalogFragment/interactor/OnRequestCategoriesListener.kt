package com.ltn.avroraflowers.ui.fragments.catalogFragment.interactor

import com.ltn.avroraflowers.model.Category
import com.ltn.avroraflowers.network.Response.ResponseCategory
import com.ltn.avroraflowers.ui.base.BaseCallback

interface OnRequestCategoriesListener: BaseCallback {
    fun onSuccessful(categories: List<Category>)
}