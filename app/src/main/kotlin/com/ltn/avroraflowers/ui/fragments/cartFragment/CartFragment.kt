package com.ltn.avroraflowers.ui.fragments.cartFragment

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
import com.ltn.avroraflowers.ui.base.BaseFragment
import com.ltn.avroraflowers.ui.fragments.cartFragment.presenter.CartFragmentPresenter
import com.ltn.avroraflowers.ui.fragments.cartFragment.view.CartFragmentView
import com.ltn.avroraflowers.ui.fragments.catalogFragment.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_cart.*

class CartFragment : BaseFragment(), CartFragmentView, CartProductsAdapter.OnClickListener,
    CartProductsAdapter.OnSelectCheckBoxLitener {

    @InjectPresenter
    lateinit var cartFragmentPresenter: CartFragmentPresenter

    lateinit var cartProductsAdapter: CartProductsAdapter

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
        initRecycler()
        cartFragmentPresenter.getCartProducts()
    }

    private fun initRecycler() {
        cartProductsAdapter = CartProductsAdapter(this, this)
        recyclerViewCart.addItemDecoration(GridSpacingItemDecoration(1, 40, true, 0))
        recyclerViewCart.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerViewCart.adapter = cartProductsAdapter
    }

    override fun showCartProducts(cartProducts: List<CartItem>) {
        cartProductsAdapter.addAll(cartProducts)
    }

    override fun onSelectedItemCheckBox(selected: Boolean) {
        cartProductsAdapter.notifyFooter(selected)
    }

    override fun onSelectedFooterCheckBox(selected: Boolean) {
        cartProductsAdapter.notifySelectedItems(selected)
    }

    override fun onDeleteButtonClick(listIds:  MutableList<Int>) {
        cartFragmentPresenter.deleteProductsFromCart(listIds)
    }

    override fun onItemClick(id: Int) {}

    override fun showProgress() {
        progressBarCart.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBarCart.visibility = View.GONE
    }

    override fun showConnectionProblem() {
    }
}