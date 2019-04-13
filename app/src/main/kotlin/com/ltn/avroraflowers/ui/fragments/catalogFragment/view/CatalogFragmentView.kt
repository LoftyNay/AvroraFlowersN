package com.ltn.avroraflowers.ui.fragments.catalogFragment.view

import com.ltn.avroraflowers.model.Category
import com.ltn.avroraflowers.ui.base.BaseView

interface CatalogFragmentView : BaseView {
    fun showCategoriesList(categories: List<Category>)
}