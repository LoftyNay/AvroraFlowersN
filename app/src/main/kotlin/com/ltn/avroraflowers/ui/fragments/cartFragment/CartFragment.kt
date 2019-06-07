package com.ltn.avroraflowers.ui.fragments.cartFragment

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.adapters.CartProductsAdapter
import com.ltn.avroraflowers.adapters.ViewPagerAdapter
import com.ltn.avroraflowers.model.Repository.CartProductsRepository
import com.ltn.avroraflowers.ui.activities.EntryActivity
import com.ltn.avroraflowers.ui.base.BaseFragment
import com.ltn.avroraflowers.ui.fragments.cartFragment.presenter.CartFragmentPresenter
import com.ltn.avroraflowers.ui.fragments.cartFragment.view.CartFragmentView
import com.ltn.avroraflowers.ui.fragments.innerProductFragment.InnerProductFragment
import com.ltn.avroraflowers.utils.Constants
import com.ltn.avroraflowers.utils.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.header_recycler_cart.*

class CartFragment : BaseFragment(), CartFragmentView, CartProductsAdapter.AdapterListener, BaseFragment.EmptyListener,
    View.OnClickListener {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbarTitle = getString(R.string.cart_item_nav)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cartFragmentPresenter.attach()
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        toConfirmCartProductsButton.setOnClickListener(this)
        datePickButtonCart.setOnClickListener(this)
    }

    private fun initRecycler() {
        cartProductsAdapter = CartProductsAdapter(this, this)
        recyclerViewCart.addItemDecoration(GridSpacingItemDecoration(1, 5, false, 0))
        recyclerViewCart.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerViewCart.adapter = cartProductsAdapter
    }

    override fun onDeleteButtonClick(listIds: MutableList<Int>) {
        showDialog(getString(R.string.delete_title),
            getString(R.string.delete_message),
            getString(R.string.confirm),
            getString(R.string.cancel),
            object : DialogListener {
                override fun onPositive() {
                    cartFragmentPresenter.deleteProductsFromCart(listIds)

                }
                override fun onNegative() {}
            }
        )
    }

    override fun invalidateRecycler(invalidateType: Int) {
        if (invalidateType == CartProductsAdapter.INVALIDATE)
            cartProductsAdapter.clear()
        cartProductsAdapter.invalidate(invalidateType)
    }

    override fun orderSended() {
        Toast.makeText(activity, getString(R.string.send_order), Toast.LENGTH_LONG)
            .show()
        cartFragmentPresenter.deleteProductsFromCart()
    }

    override fun onClick(v: View?) {
        when (v) {
            toConfirmCartProductsButton -> {
                if (tvDateCart.text == "") {
                    showDialog("Внимание", getString(R.string.pick_date_message, preferencesUtils.getName()),
                        getString(R.string.pick_date_positive), getString(R.string.cancel), object : DialogListener {
                            override fun onPositive() {
                                showDatePicker(object : DatePickerDialog.OnDateSetListener {
                                    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                                        convertAndSetData(month, dayOfMonth, year)
                                        showDialog(preferencesUtils.getName()!!,
                                            getString(R.string.confirm_order_message, tvDateCart.text, tvCity.text),
                                            getString(R.string.confirm),
                                            getString(R.string.cancel),
                                            object : DialogListener {
                                                override fun onPositive() {
                                                    cartFragmentPresenter.sendOrder()
                                                }

                                                override fun onNegative() {
                                                    Toast.makeText(
                                                        activity,
                                                        getString(R.string.error_send_order),
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                }
                                            })
                                    }
                                })
                            }

                            override fun onNegative() {
                                Toast.makeText(activity, getString(R.string.error_send_order), Toast.LENGTH_LONG).show()
                            }
                        })
                } else {
                    showDialog(preferencesUtils.getName()!!,
                        getString(R.string.confirm_order_message, tvDateCart.text, tvCity.text),
                        getString(R.string.confirm),
                        getString(R.string.cancel),
                        object : DialogListener {
                            override fun onPositive() {
                                cartFragmentPresenter.sendOrder()
                            }

                            override fun onNegative() {
                                Toast.makeText(activity, getString(R.string.error_send_order), Toast.LENGTH_LONG).show()
                            }
                        })
                }
            }
            datePickButtonCart -> {
                showDatePicker(object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                        convertAndSetData(month, dayOfMonth, year)
                    }
                })
            }
        }
    }


    override fun userLogin(status: Boolean) {
        when (status) {
            true -> {
                contentBlock.visibility = View.VISIBLE
                footerCart.visibility = View.VISIBLE
                hideNeedAutorizationBlock()
                CartProductsRepository.getInstance().callUpdate()
            }
            false -> {
                hideEmptyBlock()
                contentBlock.visibility = View.GONE
                footerCart.visibility = View.GONE
                showNeedAutorizationBlock(R.string.autorization_message_cart, View.OnClickListener {
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

    override fun showConnectionProblem() {
        Toast.makeText(activity, "Ошибка соединения, попробуйте позже", Toast.LENGTH_LONG).show()
    }

    override fun showCountOnFooter(countPerPack: Int, countPack: Int) {
        inPackCount.text = countPerPack.toString()
        packCount.text = countPack.toString()
    }

    override fun onShowEmpty() {
        if (preferencesUtils.isLogin()) {
            showEmptyBlock(
                getString(R.string.empty_cart),
                "В каталог",
                View.OnClickListener { mContext.setPagerItem(ViewPagerAdapter.CATALOG_FRAGMENT) })
            contentBlock.visibility = View.GONE
            footerCart.visibility = View.GONE
        } else
            onHideEmpty()
    }

    override fun onHideEmpty() {
        hideEmptyBlock()
        contentBlock.visibility = View.VISIBLE
        footerCart.visibility = View.VISIBLE
    }

    private fun convertAndSetData(month: Int, dayOfMonth: Int, year: Int) {
        val mMonth = month.plus(1).toString()
        val mDayOfMonth = dayOfMonth.toString()
        tvDateCart.text = getString(
            R.string.date,
            if (mDayOfMonth.length == 1) "0$mDayOfMonth" else mDayOfMonth,
            if (mMonth.length == 1) "0$mMonth" else mMonth,
            year
        )
    }

    override fun onItemClick(id: Int, title: String) {
        val fragment = InnerProductFragment.getInstance(id, title)
        parentFragment?.childFragmentManager?.beginTransaction()
            ?.add(R.id.cartFragmentContainer, fragment, InnerProductFragment.TAG)
            ?.show(fragment)
            ?.hide(this)
            ?.addToBackStack(Constants.STACK)
            ?.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        cartFragmentPresenter.destroy()
    }

    override fun showProgress() {
        progressBarCart.visibility = View.VISIBLE
        contentBlock.isEnabled = false
        footerCart.isEnabled = false
    }

    override fun hideProgress() {
        progressBarCart.visibility = View.GONE
        footerCart.isEnabled = true
        contentBlock.isEnabled = true
    }

    override fun showLoadingDialog() {
        mContext.showLoadingDialog()
    }

    override fun hideLoadingDialog() {
        mContext.closeDialogs()
    }
}