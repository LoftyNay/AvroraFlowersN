package com.ltn.avroraflowers.ui.fragments.catalogFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.adapters.CategoriesAdapter
import com.ltn.avroraflowers.model.Category
import com.ltn.avroraflowers.ui.base.BaseFragment

class CatalogFragment : BaseFragment() {

    companion object {
        fun newInstance(): CatalogFragment {
            return CatalogFragment()
        }
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_catalog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //FIXME
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewCatalogFragment)
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.layoutManager = GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(2, 40, true, 0))

        val categoriesAdapter = CategoriesAdapter()
        categoriesAdapter.addCategory(Category("d1111", "2332423"))
        categoriesAdapter.addCategory(Category("qwdwqf", "2332423"))
        categoriesAdapter.addCategory(Category("1212", "2332423"))
        categoriesAdapter.addCategory(Category("saxx", "2332423"))
        categoriesAdapter.addCategory(Category("d efsd12f1311", "2332423"))
        categoriesAdapter.addCategory(Category("d1 2f1311", "2332423"))
        categoriesAdapter.addCategory(Category("111", "2332423"))
        categoriesAdapter.addCategory(Category("dge22", "2332423"))
        categoriesAdapter.addCategory(Category("dge22", "2332423"))
        categoriesAdapter.addCategory(Category("dge22", "2332423"))
        categoriesAdapter.addCategory(Category("dge22", "2332423"))
        categoriesAdapter.addCategory(Category("dge22", "2332423"))
        categoriesAdapter.addCategory(Category("dge22", "2332423"))
        categoriesAdapter.addCategory(Category("dge22", "2332423"))
        categoriesAdapter.addCategory(Category("dge22", "2332423"))
        categoriesAdapter.addCategory(Category("dge22", "2332423"))
        categoriesAdapter.addCategory(Category("444", "2332423"))
        categoriesAdapter.addCategory(Category("22", "2332423"))
        recyclerView.adapter = categoriesAdapter

    }
}