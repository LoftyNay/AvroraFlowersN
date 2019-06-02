package com.ltn.avroraflowers.ui.fragments.ordersFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.adapters.OrdersAdapter
import com.ltn.avroraflowers.adapters.ViewPagerAdapter
import com.ltn.avroraflowers.model.Repository.OrdersRepository
import com.ltn.avroraflowers.ui.activities.EntryActivity
import com.ltn.avroraflowers.ui.base.BaseFragment
import com.ltn.avroraflowers.ui.fragments.innerOrderFragment.InnerOrderFragment
import com.ltn.avroraflowers.ui.fragments.ordersFragment.presenter.OrdersFragmentPresenter
import com.ltn.avroraflowers.ui.fragments.ordersFragment.view.OrdersFragmentView
import com.ltn.avroraflowers.utils.Constants
import com.ltn.avroraflowers.utils.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_orders.*

class OrdersFragment : BaseFragment(), OrdersFragmentView, OrdersAdapter.OnCardItemClickListener,
    BaseFragment.EmptyListener {

    @InjectPresenter
    lateinit var ordersFragmentPresenter: OrdersFragmentPresenter

    private lateinit var ordersAdapter: OrdersAdapter

    companion object {
        val TAG = "OrdersFragment"
        fun getInstance(): OrdersFragment {
            return OrdersFragment()
        }
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ordersFragmentPresenter.attach()
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        (incToolbarOrders as Toolbar).title = resources.getString(R.string.orders_item_nav)
    }

    private fun initRecycler() {
        ordersAdapter = OrdersAdapter(this, this)
        recyclerViewOrdersFragment.addItemDecoration(
            GridSpacingItemDecoration(
                1,
                40,
                true,
                0
            )
        )
        recyclerViewOrdersFragment.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerViewOrdersFragment.adapter = ordersAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        ordersFragmentPresenter.destroy()
    }

    override fun onItemClick(id: Int, date: String) {
        val fragment = InnerOrderFragment.getInstance(id, date)
        parentFragment?.childFragmentManager?.beginTransaction()
            ?.add(R.id.fragmentOrdersContainer, fragment, InnerOrderFragment.TAG)
            ?.show(fragment)
            ?.hide(this)
            ?.addToBackStack(Constants.STACK)
            ?.commit()
    }

    override fun invalidateRecycler() {
        ordersAdapter.invalidate()
    }

    override fun userLogin(status: Boolean) {
        when (status) {
            true -> {
                OrdersRepository.getInstance().callUpdate()
                hideNeedAutorizationBlock()
                recyclerViewOrdersFragment.visibility = View.VISIBLE
                invalidateRecycler()
            }
            false -> {
                hideEmptyBlock()
                recyclerViewOrdersFragment.visibility = View.GONE
                showNeedAutorizationBlock(R.string.autorization_message_orders, View.OnClickListener {
                    startActivity(
                        Intent(
                            activity,
                            EntryActivity::class.java
                        )
                    )
                })
            }
        }
    }

    override fun onShowEmpty() {
        if (preferencesUtils.isLogin()) {
            recyclerViewOrdersFragment.visibility = View.GONE
            showEmptyBlock("Вы еще ничего не заказывали, оформить заказ можно в корзине", "В корзину",
                View.OnClickListener { mContext.setPagerItem(ViewPagerAdapter.CART_FRAGMENT) })
        } else {
            onHideEmpty()
        }
    }

    override fun onHideEmpty() {
        recyclerViewOrdersFragment.visibility = View.VISIBLE
        hideEmptyBlock()
    }

    override fun showProgress() {
        progressBarOrders.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBarOrders.visibility = View.GONE
    }

    override fun showConnectionProblem() {
    }
}