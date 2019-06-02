package com.ltn.avroraflowers.ui.fragments.searchFragment

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ltn.avroraflowers.App
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.adapters.ProductsAdapter
import com.ltn.avroraflowers.model.Product
import com.ltn.avroraflowers.ui.base.BaseFragment
import com.ltn.avroraflowers.ui.fragments.innerProductFragment.InnerProductFragment
import com.ltn.avroraflowers.ui.fragments.searchFragment.presenter.SearchFragmentPresenter
import com.ltn.avroraflowers.ui.fragments.searchFragment.view.SearchFragmentView
import com.ltn.avroraflowers.utils.Constants
import com.ltn.avroraflowers.utils.GridSpacingItemDecoration
import com.ltn.avroraflowers.utils.Utils
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.toolbar_search_edit.*
import javax.inject.Inject

class SearchFragment : BaseFragment(), SearchFragmentView, ProductsAdapter.OnCardItemClickListener,
    ProductsAdapter.OnAddToCartClickListener {

    @Inject
    lateinit var utils: Utils

    lateinit var searchFragmentPresenter: SearchFragmentPresenter
    private lateinit var searchResults: ProductsAdapter

    companion object {
        val TAG = "SearchFragment"
        fun getInstance() = SearchFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component.inject(this)
        super.onCreate(savedInstanceState)
        searchFragmentPresenter = SearchFragmentPresenter(this)
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return layoutInflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        searchResults.invalidate(preferencesUtils.isLogin())

        searchFragmentPresenter.search("") //for init products list

        searchEditButtonClear.setOnClickListener { searchEdit.setText("") }

        searchEdit.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                utils.hideSoftKeyboard(searchEdit)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        searchEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Handler().post {
                    if (!s.isNullOrEmpty()) {
                        searchEditButtonClear.visibility = View.VISIBLE
                        searchEditButtonSearch.visibility = View.GONE
                    } else {
                        searchEditButtonClear.visibility = View.GONE
                        searchEditButtonSearch.visibility = View.VISIBLE
                    }
                }
                searchFragmentPresenter.search(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun initRecycler() {
        searchResults = ProductsAdapter(this, this)
        recyclerSearch.addItemDecoration(
            GridSpacingItemDecoration(
                1,
                40,
                true,
                0
            )
        )
        recyclerSearch.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerSearch.adapter = searchResults
    }

    override fun showResults(results: List<Product>) {
        searchResults.addAll(results)
    }


    override fun showEmpty() {
    }

    override fun onItemClick(id: Int, title: String) {
        val fragment = InnerProductFragment.getInstance(
            id, title
        )
        parentFragment?.childFragmentManager?.beginTransaction()
            ?.add(R.id.searchContainer, fragment, InnerProductFragment.TAG)
            ?.show(fragment)
            ?.hide(this)
            ?.addToBackStack(TAG)
            ?.commit()
    }

    override fun onAddToCartClick(id: Int, count: Int, perPack: Int) {
        searchFragmentPresenter.addToCart(id, count, perPack)
    }

    override fun showProgress() {
        progressBarSearch.visibility = View.VISIBLE
        searchEdit.isEnabled = false
    }

    override fun hideProgress() {
        progressBarSearch.visibility = View.GONE
        searchEdit.isEnabled = true
        utils.showSoftKeyboard(searchEdit)
    }

    override fun showConnectionProblem() {
        utils.hideSoftKeyboard(searchEdit)
        Toast.makeText(activity, "Ошибка соединения, попробуйте позже", Toast.LENGTH_LONG).show()
    }
}