package com.ltn.avroraflowers.ui.fragments.mainFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.adapters.ViewPagerAdapter
import com.ltn.avroraflowers.model.Repository.CartProductsRepository
import com.ltn.avroraflowers.model.Repository.OrdersRepository
import com.ltn.avroraflowers.ui.activities.EntryActivity
import com.ltn.avroraflowers.ui.base.BaseFragment
import com.ltn.avroraflowers.ui.fragments.mainFragment.presenter.MainFragmentPresenter
import com.ltn.avroraflowers.ui.fragments.mainFragment.view.MainFragmentView
import com.ltn.avroraflowers.ui.fragments.selectSavedOrderDIalogFragment.SelectSavedOrderDialog
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment(), MainFragmentView {

    private lateinit var mainFragmentPresenter: MainFragmentPresenter

    companion object {
        fun getInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainFragmentPresenter = MainFragmentPresenter(this)
        toolbarTitle = resources.getString(R.string.app_name)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        autorizationButtonFragmentMain.setOnClickListener { startActivity(Intent(context, EntryActivity::class.java)) }
        createOrderButton.setOnClickListener { mContext.setPagerItem(ViewPagerAdapter.CART_FRAGMENT) }
        repeatLastOrderButton.setOnClickListener {
            showDialogRepeatLastOrder()
        }
        loadLastOrderButton.setOnClickListener {
            SelectSavedOrderDialog.newInstance().show(fragmentManager!!, SelectSavedOrderDialog.TAG)
        }
        exitButtonFragmentMain.setOnClickListener {
            showDialog(
                preferencesUtils.getName()!!,
                getString(R.string.exit_profile),
                getString(R.string.yes),
                getString(R.string.no),
                object : DialogListener {
                    override fun onPositive() {
                        preferencesUtils.clearUserData()
                        mContext.clearBackStack()
                        OrdersRepository.getInstance().clear()
                        CartProductsRepository.getInstance().clear()
                    }

                    override fun onNegative() {

                    }
                }
            )
        }
    }

    override fun navigateToCart() {
        mContext.setPagerItem(ViewPagerAdapter.CART_FRAGMENT)
    }

    private fun showDialogRepeatLastOrder() {
        showDialog(
            getString(R.string.load_last_order),
            getString(R.string.load_last_order_message),
            getString(R.string.continue_b),
            getString(R.string.cancel),
            object : DialogListener {
                override fun onPositive() {
                    mainFragmentPresenter.repeatLastOrder()
                }

                override fun onNegative() {}
            }
        )
    }

    override fun userLogin(status: Boolean) {
        when (status) {
            true -> {
                mainHelloTextView.visibility = View.VISIBLE
                exitButtonFragmentMain.visibility = View.VISIBLE
                createOrderButton.visibility = View.VISIBLE
                repeatLastOrderButton.visibility = View.VISIBLE
                loadLastOrderButton.visibility = View.VISIBLE
                ivMainAvrora.visibility = View.GONE
                autorizationButtonFragmentMain.visibility = View.GONE
                mainHelloTextView.text = resources.getString(R.string.hello, preferencesUtils.getName())
            }
            false -> {
                mainHelloTextView.visibility = View.GONE
                exitButtonFragmentMain.visibility = View.GONE
                ivMainAvrora.visibility = View.VISIBLE
                autorizationButtonFragmentMain.visibility = View.VISIBLE
                createOrderButton.visibility = View.GONE
                repeatLastOrderButton.visibility = View.GONE
                loadLastOrderButton.visibility = View.GONE
            }
        }
    }

    override fun showLoadingDialog() {
        mContext.showLoadingDialog()
    }

    override fun hideLoadingDialog() {
        mContext.closeDialogs()
    }

    override fun showProgress() {}

    override fun hideProgress() {}
}