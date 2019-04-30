package com.ltn.avroraflowers.ui.fragments.productsFragment

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.adapters.ProductsAdapter
import com.ltn.avroraflowers.model.Product
import com.ltn.avroraflowers.ui.base.BaseFragment
import com.ltn.avroraflowers.ui.fragments.catalogFragment.CatalogFragment
import com.ltn.avroraflowers.ui.fragments.catalogFragment.GridSpacingItemDecoration
import com.ltn.avroraflowers.ui.fragments.productsFragment.presenter.ProductsFragmentPresenter
import com.ltn.avroraflowers.ui.fragments.productsFragment.view.ProductsFragmentView
import kotlinx.android.synthetic.main.fragment_catalog_products.*

class ProductsFragment : BaseFragment(), ProductsFragmentView, ProductsAdapter.OnCardItemClickListener,
    ProductsAdapter.OnAddToCartClickListener {

    @InjectPresenter
    lateinit var productsFragmentPresenter: ProductsFragmentPresenter

    lateinit var productsAdapter: ProductsAdapter

    companion object {
        val KEY_ID = "id"

        val TAG = "ProductsFragment"

        fun getInstance(id: Int): ProductsFragment {
            val fragment = ProductsFragment()
            val bundle = Bundle()
            bundle.putInt(KEY_ID, id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_catalog_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        productsFragmentPresenter.attach(view.context)
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        val categoryId = arguments?.getInt(KEY_ID)
        if (categoryId != null) {
            productsFragmentPresenter.getProductsFromServerByCategoryId(categoryId)
        }
    }

    private fun initRecycler() {
        productsAdapter = ProductsAdapter(this, this)
        recyclerViewProductsFragment.addItemDecoration(GridSpacingItemDecoration(1, 40, true, 0))
        recyclerViewProductsFragment.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerViewProductsFragment.adapter = productsAdapter
    }

    override fun onItemClick(id: Int) {
        Toast.makeText(activity, "item click" , Toast.LENGTH_SHORT).show()
    }

    override fun onAddToCartClick(id: Int) {
        Toast.makeText(activity, "button click" , Toast.LENGTH_SHORT).show()
    }

    override fun showProducts(products: List<Product>) {
        productsAdapter.addAll(products)
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun showConnectionProblem() {
    }
}