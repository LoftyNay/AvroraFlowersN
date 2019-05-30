package com.ltn.avroraflowers.ui.fragments.searchFragment.view

import com.ltn.avroraflowers.model.Product
import com.ltn.avroraflowers.ui.base.BaseView

interface SearchFragmentView: BaseView {
    fun showResults(results: List<Product>)
    fun showEmpty()
}