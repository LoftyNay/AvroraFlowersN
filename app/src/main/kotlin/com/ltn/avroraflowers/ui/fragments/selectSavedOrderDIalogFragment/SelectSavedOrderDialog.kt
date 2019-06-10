package com.ltn.avroraflowers.ui.fragments.selectSavedOrderDIalogFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.adapters.SelectSavedOrderAdapter
import com.ltn.avroraflowers.adapters.ViewPagerAdapter
import com.ltn.avroraflowers.model.SavedProduct
import com.ltn.avroraflowers.model.SavedProductKey
import com.ltn.avroraflowers.network.Response.SavedOrder
import com.ltn.avroraflowers.ui.base.BaseDialogFragment
import com.ltn.avroraflowers.ui.base.BaseFragment
import com.ltn.avroraflowers.ui.dialogs.LoadingDialog
import com.ltn.avroraflowers.ui.fragments.selectSavedOrderDIalogFragment.presenter.SelectSavedOrderDialogPresenter
import com.ltn.avroraflowers.ui.fragments.selectSavedOrderDIalogFragment.view.SelectSavedOrderDialogView
import com.ltn.avroraflowers.utils.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.dialog_select_saved_order.*


class SelectSavedOrderDialog : BaseDialogFragment(), SelectSavedOrderDialogView, BaseFragment.EmptyListener,
    SelectSavedOrderAdapter.OnCardItemClickListener {

    lateinit var selectSavedOrderDialogPresenter: SelectSavedOrderDialogPresenter
    lateinit var adapter: SelectSavedOrderAdapter

    companion object {
        const val TAG = "SelectSavedOrderDialog_tag"
        fun newInstance() = SelectSavedOrderDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectSavedOrderDialogPresenter = SelectSavedOrderDialogPresenter(this)
        setStyle(STYLE_NO_TITLE, R.style.DialogFragment)
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_select_saved_order, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigationIcon(savedOrdersToolbar, getString(R.string.saved_orders))
        initRecycler()
        selectSavedOrderDialogPresenter.loadSavedOrders()
    }

    private fun setNavigationIcon(toolbar: View, title: String) {
        (toolbar as Toolbar).title = title
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow)
        toolbar.setNavigationOnClickListener {
            dismiss()
        }
    }

    private fun initRecycler() {
        adapter = SelectSavedOrderAdapter(this, this)
        rvSavedOrders.addItemDecoration(
            GridSpacingItemDecoration(
                1,
                40,
                true,
                0
            )
        )
        rvSavedOrders.layoutManager = LinearLayoutManager(activity as Context, RecyclerView.VERTICAL, false)
        rvSavedOrders.adapter = adapter
    }

    override fun invalidateRecycler() {
        adapter.invalidate()
    }

    override fun showSavedOrders(savedOrders: HashMap<SavedProductKey, MutableList<SavedProduct>>) {
        adapter.addAllProducts(savedOrders)
    }

    override fun onItemClick(savedOrderName: String, saveOrderId: Int) {
        mContext.showDialog(
            savedOrderName,
            getString(R.string.load_saved_order),
            getString(R.string.continue_b),
            getString(R.string.cancel),
            object : BaseFragment.DialogListener {
                override fun onPositive() {
                    selectSavedOrderDialogPresenter.addSavedOrderToCart(saveOrderId)
                }
                override fun onNegative() {}
            }
        )
    }

    override fun onShowEmpty() {
        tvEmpty.visibility = View.VISIBLE
        rvSavedOrders.visibility = View.GONE
    }

    override fun onHideEmpty() {
        tvEmpty.visibility = View.GONE
        rvSavedOrders.visibility = View.VISIBLE
    }

    override fun showProgress() {
        rvSavedOrders.visibility = View.GONE
        progressSavedOrders.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        rvSavedOrders.visibility = View.VISIBLE
        progressSavedOrders.visibility = View.GONE
    }

    override fun showConnectionProblem() {
        Toast.makeText(activity, "Ошибка соединения, попробуйте позже", Toast.LENGTH_LONG).show()
    }

    override fun showLoadingDialog() {
        mContext.showLoadingDialog()
    }

    override fun hideLoadingDialog() {
        mContext.closeDialogs()
    }

    override fun resultOk(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
        mContext.setPagerItem(ViewPagerAdapter.CART_FRAGMENT)
        dismiss()
    }
}