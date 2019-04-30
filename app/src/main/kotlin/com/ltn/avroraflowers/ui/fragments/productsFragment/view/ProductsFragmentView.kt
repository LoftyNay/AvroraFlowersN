package com.ltn.avroraflowers.ui.fragments.productsFragment.view

import com.ltn.avroraflowers.model.Product
import com.ltn.avroraflowers.ui.base.BaseView

interface ProductsFragmentView: BaseView {
    fun showProducts(products: List<Product>)
}