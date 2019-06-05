package com.ltn.avroraflowers.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.adapters.ViewPagerAdapter
import com.ltn.avroraflowers.ui.activities.EntryActivity
import com.ltn.avroraflowers.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {

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
        toolbarTitle = resources.getString(R.string.app_name)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        autorizationButtonFragmentMain.setOnClickListener { startActivity(Intent(context, EntryActivity::class.java)) }
        createOrderButton.setOnClickListener { mContext.setPagerItem(ViewPagerAdapter.CART_FRAGMENT) }
        repeatLastOrderButton.setOnClickListener { }
        loadLastOrderButton.setOnClickListener { }
        exitButtonFragmentMain.setOnClickListener {
            showDialog(
                preferencesUtils.getName()!!,
                getString(R.string.exit_profile),
                getString(R.string.yes),
                getString(R.string.no),
                object : DialogListener {
                    override fun onPositive() {
                        preferencesUtils.clearUserData()
                    }

                    override fun onNegative() {

                    }
                }
            )
        }
    }

    fun showRepeatLastOrderDialog() {
        //TODO
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

    override fun showProgress() {}

    override fun hideProgress() {}
}