package com.ltn.avroraflowers.ui.fragments.catalogFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.adapters.CategoriesAdapter
import com.ltn.avroraflowers.model.Category
import com.ltn.avroraflowers.ui.base.BaseFragment
import com.ltn.avroraflowers.ui.fragments.catalogFragment.presenter.CatalogFragmentPresenter
import com.ltn.avroraflowers.ui.fragments.catalogFragment.view.CatalogFragmentView
import com.ltn.avroraflowers.ui.fragments.productsFragment.ProductsFragment
import com.ltn.avroraflowers.utils.Constants


class CatalogFragment : BaseFragment(), CatalogFragmentView, CategoriesAdapter.OnCardItemClickListener {

    companion object {
        val TAG = "CatalogFragment"

        lateinit var catalogFragment: CatalogFragment

        fun getInstance(): CatalogFragment {
            return CatalogFragment()
        }
    }

    @InjectPresenter
    lateinit var catalogFragmentPresenter: CatalogFragmentPresenter

    lateinit var categoriesAdapter: CategoriesAdapter

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_catalog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        catalogFragmentPresenter.attach(view.context)
        super.onViewCreated(view, savedInstanceState)
        initRecycler(view)
        catalogFragmentPresenter.loadCategories()
    }

    private fun initRecycler(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewCatalogFragment)
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.layoutManager = GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(2, 40, true, 0))
        categoriesAdapter = CategoriesAdapter(this)
        recyclerView.adapter = categoriesAdapter
    }

    override fun onItemClick(id: Int) {
        val fragment = ProductsFragment.getInstance(id)
        parentFragment?.childFragmentManager?.beginTransaction()
            ?.add(R.id.fragmentCatalogContainer, fragment, ProductsFragment.TAG)
            ?.show(fragment)
            ?.hide(this)
            ?.addToBackStack(Constants.CATALOG_STACK)
            ?.commit()
    }

    override fun showCategoriesList(categories: List<Category>) {
        categoriesAdapter.addAll(categories)
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun showConnectionProblem() {
    }
}