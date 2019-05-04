package com.ltn.avroraflowers.ui.fragments.cartFragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.containsValue
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.adapters.CartProductsAdapter
import com.ltn.avroraflowers.model.CartItem
import com.ltn.avroraflowers.model.Repository.CartProductsRepository
import com.ltn.avroraflowers.ui.base.BaseFragment
import com.ltn.avroraflowers.ui.fragments.cartFragment.presenter.CartFragmentPresenter
import com.ltn.avroraflowers.ui.fragments.cartFragment.view.CartFragmentView
import com.ltn.avroraflowers.ui.fragments.catalogFragment.GridSpacingItemDecoration
import com.ltn.avroraflowers.ui.fragments.innerProductFragment.InnerProductFragment
import com.ltn.avroraflowers.ui.fragments.productsFragment.ProductsFragment
import com.ltn.avroraflowers.utils.Constants
import kotlinx.android.synthetic.main.footer_recycler_item.*
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar_with_search.*

class CartFragment : BaseFragment(), CartFragmentView, CartProductsAdapter.OnClickListener, View.OnClickListener {

    @InjectPresenter
    lateinit var cartFragmentPresenter: CartFragmentPresenter

    private lateinit var cartProductsAdapter: CartProductsAdapter

    companion object {
        fun getInstance(): CartFragment {
            return CartFragment()
        }
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cartFragmentPresenter.attach(view.context)
        super.onViewCreated(view, savedInstanceState)
        toolbarSearch.title = resources.getString(R.string.cart_item_nav)
        initRecycler()
        CartProductsRepository.getInstance().callUpdate()
    }

    private fun initRecycler() {
        cartProductsAdapter = CartProductsAdapter(this)
        recyclerViewCart.addItemDecoration(GridSpacingItemDecoration(1, 5, false, 0))
        recyclerViewCart.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerViewCart.adapter = cartProductsAdapter
    }

    override fun onDeleteButtonClick(listIds: MutableList<Int>) {
        cartFragmentPresenter.deleteProductsFromCart(listIds)
    }

    override fun invalidateRecycler(invalidateType: Int) {
        cartProductsAdapter.invalidate(invalidateType)
    }

    override fun onClick(v: View?) {
        when(v) {
            toConfirmCartProductsButton -> {
                cartFragmentPresenter.sendOrder()
            }
        }
    }

    override fun onItemClick(id: Int) {
        val fragment = InnerProductFragment.getInstance()
        parentFragment?.childFragmentManager?.beginTransaction()
            ?.add(R.id.cartFragmentContainer, fragment, ProductsFragment.TAG)
            ?.show(fragment)
            ?.hide(this)
            ?.addToBackStack(Constants.CATALOG_STACK)
            ?.commit()
    }

    override fun showProgress() {
        progressBarCart.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBarCart.visibility = View.GONE
    }

    override fun showConnectionProblem() {
    }
}