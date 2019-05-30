package com.ltn.avroraflowers.ui.fragments.innerProductFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.arellomobile.mvp.presenter.InjectPresenter
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.model.Product
import com.ltn.avroraflowers.ui.base.BaseFragment
import com.ltn.avroraflowers.ui.fragments.innerProductFragment.presenter.InnerProductFragmentPresenter
import com.ltn.avroraflowers.ui.fragments.innerProductFragment.view.InnerProductFragmentView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_products_inner.*

class InnerProductFragment : BaseFragment(), InnerProductFragmentView {

    @InjectPresenter
    lateinit var innerProductFragmentPresenter: InnerProductFragmentPresenter

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
        super.onViewCreated(view, savedInstanceState)
        val productId = arguments?.getInt(KEY_ID)
        val title = arguments?.getString(KEY_TITLE)
        if (productId != null) {
            innerProductFragmentPresenter.getProduct(productId)
            (toolbarInnerProd as Toolbar).title = title
        }

        minusInCardInnerProduct.setOnClickListener {  }
        plusInCardInnerProduct.setOnClickListener {  }
        countInnerProduct
//FIXME
        addToCartInnerProduct.setOnClickListener {  }
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

    override fun showConnectionProblem() {
    }
}