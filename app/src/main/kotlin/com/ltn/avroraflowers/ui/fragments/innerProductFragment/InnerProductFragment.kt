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
        fun getInstance(id: Int): InnerProductFragment {
            val fragment = InnerProductFragment()
            val bundle = Bundle()
            bundle.putInt(KEY_ID, id)
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
        if (productId != null) {
            Log.d("GLL", productId.toString())
            innerProductFragmentPresenter.getProduct(productId)
        }
        (toolbarInnerProd as Toolbar).title = "inner"
    }

    override fun showProductInfo(product: Product) {
        Picasso.get().load(product.image).fit().centerCrop().into(imageProductInner)
        titleProductInner.text = product.title
        colorProductInner.text = product.color
        descriptionInnerProduct.text = product.description
    }

    override fun showProgress() {
        progressBarProductInner.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBarProductInner.visibility = View.GONE
    }

    override fun showConnectionProblem() {
    }
}