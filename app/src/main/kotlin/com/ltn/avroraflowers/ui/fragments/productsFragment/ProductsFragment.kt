package com.ltn.avroraflowers.ui.fragments.productsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.adapters.ProductsAdapter
import com.ltn.avroraflowers.model.Product
import com.ltn.avroraflowers.ui.base.BaseFragment
import com.ltn.avroraflowers.utils.GridSpacingItemDecoration
import com.ltn.avroraflowers.ui.fragments.innerProductFragment.InnerProductFragment
import com.ltn.avroraflowers.ui.fragments.productsFragment.presenter.ProductsFragmentPresenter
import com.ltn.avroraflowers.ui.fragments.productsFragment.view.ProductsFragmentView
import com.ltn.avroraflowers.utils.Constants
import kotlinx.android.synthetic.main.fragment_catalog_products.*
import kotlinx.android.synthetic.main.toolbar_with_search.*

class ProductsFragment : BaseFragment(), ProductsFragmentView, ProductsAdapter.OnCardItemClickListener,
    ProductsAdapter.OnAddToCartClickListener {

    @InjectPresenter
    lateinit var productsFragmentPresenter: ProductsFragmentPresenter

    lateinit var productsAdapter: ProductsAdapter

    companion object {
        val KEY_ID = "id"
        val KEY_TITLE = "title"
        val TAG = "ProductsFragment"

        fun getInstance(id: Int, title: String): ProductsFragment {
            val fragment = ProductsFragment()
            val bundle = Bundle()
            bundle.putInt(KEY_ID, id)
            bundle.putString(KEY_TITLE, title)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_catalog_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        productsFragmentPresenter.attach()
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        val categoryId = arguments?.getInt(KEY_ID)
        val title =  arguments?.getString(KEY_TITLE)
        if (categoryId != null) {
            toolbarSearch.title = title
            productsFragmentPresenter.getProductsFromServerByCategoryId(categoryId)
        }
    }

    private fun initRecycler() {
        productsAdapter = ProductsAdapter(this, this)
        recyclerViewProductsFragment.addItemDecoration(
            GridSpacingItemDecoration(
                1,
                40,
                true,
                0
            )
        )
        recyclerViewProductsFragment.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerViewProductsFragment.adapter = productsAdapter
    }

    override fun onItemClick(id: Int, title: String) {
        val fragment = InnerProductFragment.getInstance(id, title)
        parentFragment?.childFragmentManager?.beginTransaction()
            ?.add(R.id.fragmentCatalogContainer, fragment, InnerProductFragment.TAG)
            ?.show(fragment)
            ?.hide(this)
            ?.addToBackStack(Constants.STACK)
            ?.commit()
    }

    override fun onAddToCartClick(id: Int, count: Int, perPack: Int) {
        productsFragmentPresenter.addProductToCart(id, count, perPack)
    }

    override fun userLogin(status: Boolean) {
        productsAdapter.invalidate(status)
    }

    override fun showProducts(products: List<Product>) {
        productsAdapter.addAll(products)
    }



    override fun showProgress() {
        progressBarProducts.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBarProducts.visibility = View.GONE
    }

    override fun showConnectionProblem() {
        Toast.makeText(activity, "Ошибка соединения, попробуйте позже", Toast.LENGTH_LONG).show()
    }
}