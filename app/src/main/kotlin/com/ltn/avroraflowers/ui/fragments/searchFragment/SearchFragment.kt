package com.ltn.avroraflowers.ui.fragments.searchFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.adapters.ProductsAdapter
import com.ltn.avroraflowers.model.Product
import com.ltn.avroraflowers.ui.fragments.searchFragment.presenter.SearchFragmentPresenter
import com.ltn.avroraflowers.ui.fragments.searchFragment.view.SearchFragmentView
import com.ltn.avroraflowers.utils.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.toolbar_search_edit.*

class SearchFragment : DialogFragment(), SearchFragmentView, ProductsAdapter.OnCardItemClickListener,
    ProductsAdapter.OnAddToCartClickListener {

    lateinit var searchFragmentPresenter: SearchFragmentPresenter
    private lateinit var searchResults: ProductsAdapter

    companion object {
        val TAG = "SearchFragment"

        fun getInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchFragmentPresenter = SearchFragmentPresenter(this)
        setStyle(STYLE_NO_TITLE, R.style.DialogFragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        searchFragmentPresenter.search("")
        searchEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
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
    }

    override fun onAddToCartClick(id: Int, count: Int, perPack: Int) {
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun showConnectionProblem() {

    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }
}