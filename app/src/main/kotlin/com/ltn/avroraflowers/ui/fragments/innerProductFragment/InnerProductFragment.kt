package com.ltn.avroraflowers.ui.fragments.innerProductFragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.radiobutton.MaterialRadioButton
import com.ltn.avroraflowers.App
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.model.Product
import com.ltn.avroraflowers.ui.activities.EntryActivity
import com.ltn.avroraflowers.ui.base.BaseFragment
import com.ltn.avroraflowers.ui.fragments.innerProductFragment.presenter.InnerProductFragmentPresenter
import com.ltn.avroraflowers.ui.fragments.innerProductFragment.view.InnerProductFragmentView
import com.ltn.avroraflowers.utils.Constants
import com.ltn.avroraflowers.utils.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_products_inner.*
import javax.inject.Inject

class InnerProductFragment : BaseFragment(), InnerProductFragmentView {

    @InjectPresenter
    lateinit var innerProductFragmentPresenter: InnerProductFragmentPresenter

    @Inject
    lateinit var utils: Utils

    private var productId: Int? = null
    private var countPacks: Int? = null
    private var countPerPack: Int? = null

    companion object {
        val TAG = "InnerProductFragment"
        val KEY_ID = "id"
        val KEY_TITLE = "title"
        fun getInstance(id: Int, title: String): InnerProductFragment {
            val fragment = InnerProductFragment()
            val bundle = Bundle()
            bundle.putInt(KEY_ID, id)
            bundle.putString(KEY_TITLE, title)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_products_inner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        innerProductFragmentPresenter.attach()
        App.component.inject(this)
        super.onViewCreated(view, savedInstanceState)
        utils.hideSoftKeyboard(view)
        productId = arguments?.getInt(KEY_ID)
        val title = arguments?.getString(KEY_TITLE)

        if (productId != null) {
            setNavigationIcon(toolbarInnerProd, title!!)
            innerProductFragmentPresenter.getProduct(productId!!)
        }

        minusInCardInnerProduct.setOnClickListener {
            val count = Integer.decode(countInnerProduct.text.toString())
            minusCount(count)
        }

        plusInCardInnerProduct.setOnClickListener {
            val count = Integer.decode(countInnerProduct.text.toString())
            plusCount(count)
        }
    }

    override fun userLogin(status: Boolean) {
        when (status) {
            true -> {
                addToCartInnerProduct.text = getString(R.string.add_to_cart)
                addToCartInnerProduct.setOnClickListener {
                    innerProductFragmentPresenter.addToCart(productId!!, Integer.decode(countInnerProduct.text.toString()),Integer.decode(radioGroup.findViewById<MaterialRadioButton>(radioGroup.checkedRadioButtonId).text.toString()))
                }
            }
            false -> {
                addToCartInnerProduct.text = "Вход в аккаунт"
                addToCartInnerProduct.setOnClickListener {
                    startActivity(Intent(activity, EntryActivity::class.java))
                }
            }
        }
    }

    override fun showProductInfo(product: Product) {
        Picasso.get().load(product.image).into(imageProductInner)
        titleProductInner.text = product.title
        colorProductInner.text = product.color
        descriptionInnerProduct.text = product.description
    }

    override fun showProgress() {
        contentBlock.visibility = View.GONE
        progressBarProductInner.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        contentBlock.visibility = View.VISIBLE
        progressBarProductInner.visibility = View.GONE
    }

    override fun showLoadingDialog() {
        mContext.showLoadingDialog()
    }

    override fun hideLoadingDialog() {
        mContext.closeDialogs()
    }

    interface OnCardItemClickListener {
        fun onItemClick(id: Int, title: String)
    }

    private fun minusCount(count: Int) {
        if (count <= 1) {
            countInnerProduct.isEnabled = false
        } else {
            countInnerProduct.text = (count - 1).toString()
        }
    }

    private fun plusCount(count: Int) {
        if (count >= Constants.COUNT_PACK_MAX) {
            countInnerProduct.isEnabled = false
        } else {
            countInnerProduct.text = (count + 1).toString()
        }
    }
}