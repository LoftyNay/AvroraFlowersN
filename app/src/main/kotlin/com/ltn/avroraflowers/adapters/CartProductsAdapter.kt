package com.ltn.avroraflowers.adapters

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.model.CartItem
import com.ltn.avroraflowers.model.Repository.CartProductsRepository
import com.ltn.avroraflowers.ui.base.BaseFragment
import com.ltn.avroraflowers.utils.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class CartProductsAdapter(
    private val adapterListener: AdapterListener,
    private val emptyListener: BaseFragment.EmptyListener
) : RecyclerView.Adapter<ViewHolder>() {

    companion object {
        val INVALIDATE_TYPE_DELETE = 0
        val INVALIDATE_TYPE_ADD = 1
        val INVALIDATE = 2
    }

    private val TYPE_ITEM = 1
    private val TYPE_FOOTER = 2

    private var cartProducts: MutableList<CartItem> = CartProductsRepository.getInstance().getList()

    private var isFooterCheckBoxSelected: Boolean = false
    private var isVisibleDeleteButton: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        lateinit var itemView: View
        when (viewType) {
            TYPE_FOOTER -> {
                itemView = LayoutInflater.from(parent.context).inflate(R.layout.footer_recycler_item, parent, false)
                return FooterViewHolder(itemView)
            }
            else -> {
                itemView = LayoutInflater.from(parent.context).inflate(R.layout.cart_item_recycler, parent, false)
                return ItemViewHolder(itemView)
            }
        }
    }

    fun clear() {
        cartProducts.clear()
        CartProductsRepository.getInstance().getList().clear()
        invalidate()
    }

    override fun getItemCount(): Int {
        return cartProducts.size + 1
    }

    private fun notifySelectedItems(selected: Boolean) {
        cartProducts.forEach { it.checked = selected }
        isVisibleDeleteButton = selected
        notifyDataSetChanged()
    }

    private fun notifyFooter() {
        var cartItem = cartProducts.find { item -> !item.checked }
        isFooterCheckBoxSelected = cartItem == null
        cartItem = cartProducts.find { item -> item.checked } //optimize
        isVisibleDeleteButton = cartItem != null
        notifyDataSetChanged()
    }

    fun invalidate(invalidateType: Int) {
        when (invalidateType) {
            INVALIDATE_TYPE_DELETE -> {
                val iterator = cartProducts.iterator()
                var iterable = 0
                while (iterator.hasNext()) {
                    val item = iterator.next()
                    if (item.checked) {
                        iterator.remove()
                        notifyItemRemoved(iterable)
                        notifyItemRangeChanged(iterable, cartProducts.size)
                    }
                    iterable++
                }
            }
            INVALIDATE_TYPE_ADD -> {
                notifyDataSetChanged()
            }
            INVALIDATE -> {
                notifyDataSetChanged()
            }
        }
        if (cartProducts.count() == 0) {
            emptyListener.onShowEmpty()
            isFooterCheckBoxSelected = false
            isVisibleDeleteButton = false
        } else {
            emptyListener.onHideEmpty()
        }
        Handler().post {
            adapterListener.showCountOnFooter(
                cartProducts.sumBy { it.per_pack },
                cartProducts.sumBy { it.count_pack })
        }
    }

    fun invalidate() {
        if (cartProducts.count() == 0) {
            emptyListener.onShowEmpty()
            isFooterCheckBoxSelected = false
            isVisibleDeleteButton = false
        } else {
            emptyListener.onHideEmpty()
        }
        Handler().post {
            adapterListener.showCountOnFooter(
                cartProducts.sumBy { it.per_pack },
                cartProducts.sumBy { it.count_pack })
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> {
                holder.title.text = cartProducts[position].title
                holder.colorText.text = cartProducts[position].color
                holder.cartCardItem.setOnClickListener { adapterListener.onItemClick(cartProducts[position].product_id, cartProducts[position].title) }
                holder.checkItem.isChecked = cartProducts[position].checked
                holder.checkItem.setOnClickListener {
                    cartProducts[position].checked = !cartProducts[position].checked
                    notifyFooter()
                }
                initPerPackSpinner(holder, position)
                initCountPackSpinner(holder, position)
            }
            is FooterViewHolder -> {
                holder.selectAllCheckBox.isChecked = isFooterCheckBoxSelected
                holder.selectAllCheckBox.setOnClickListener {
                    notifySelectedItems(!isFooterCheckBoxSelected)
                    isFooterCheckBoxSelected = !isFooterCheckBoxSelected
                }
                holder.deleteButton.visibility = if (isVisibleDeleteButton) View.VISIBLE else View.GONE
                holder.deleteButton.setOnClickListener {
                    Observable.fromIterable(cartProducts)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter { item -> item.checked }
                        .concatMapIterable { item -> mutableListOf(item._id) }
                        .toList()
                        .subscribe { result ->
                            adapterListener.onDeleteButtonClick(result)
                            isVisibleDeleteButton = false
                        }
                }
            }
        }
    }

    class ItemViewHolder(itemView: View) : ViewHolder(itemView) {
        val cartCardItem = itemView.findViewById<MaterialCardView>(R.id.cartCardItem)
        val title = itemView.findViewById<TextView>(R.id.titleProductCart)
        val colorText = itemView.findViewById<TextView>(R.id.colorProductCart)
        val checkItem = itemView.findViewById<AppCompatCheckBox>(R.id.checkBoxItemCart)
        val perPackSpinner = itemView.findViewById<Spinner>(R.id.cartPackSpinner)
        val countPackSpinner = itemView.findViewById<Spinner>(R.id.countPackSpinner)
    }

    class FooterViewHolder(itemView: View) : ViewHolder(itemView) {
        val selectAllCheckBox = itemView.findViewById<AppCompatCheckBox>(R.id.checkBoxFooterCart)
        //val textStatus = itemView.findViewById<TextView>(R.id.textFooterCart)
        val deleteButton = itemView.findViewById<MaterialButton>(R.id.deleteItemsFooterCartButton)
    }

    override fun getItemViewType(position: Int): Int {
        when (position) {
            cartProducts.size -> return TYPE_FOOTER
            else -> return TYPE_ITEM;
        }
    }

    private fun initCountPackSpinner(holder: ItemViewHolder, position: Int) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            holder.countPackSpinner.background = holder.countPackSpinner.context.getDrawable(R.drawable.spinner_outline_no_arrow)
        }
        val listCountSpinner = Constants.LIST_COUNT_PACK
        val arrayAdapter = ArrayAdapter<Int>(
            holder.cartCardItem.context,
            R.layout.spinner_row,
            listCountSpinner
        )
        holder.countPackSpinner.adapter = arrayAdapter
        for (item in listCountSpinner) {
            if (item == cartProducts[position].count_pack)
                holder.countPackSpinner.setSelection(listCountSpinner.indexOf(item))
        }
        holder.countPackSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                cartProducts[position].count_pack = listCountSpinner[pos]
                Handler().post {
                    adapterListener.showCountOnFooter(
                        cartProducts.sumBy { it.per_pack },
                        cartProducts.sumBy { it.count_pack })
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun initPerPackSpinner(holder: ItemViewHolder, position: Int) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            holder.perPackSpinner.background = holder.perPackSpinner.context.getDrawable(R.drawable.spinner_outline_no_arrow)
        }
        val listPerPackSpinner = Constants.LIST_PER_PACK
        holder.perPackSpinner.adapter = ArrayAdapter<Int>(
            holder.cartCardItem.context,
            R.layout.spinner_row,
            listPerPackSpinner
        )
        for (item in listPerPackSpinner) {
            if (item == cartProducts[position].per_pack) {
                holder.perPackSpinner.setSelection(listPerPackSpinner.indexOf(item))
            }
        }
        holder.perPackSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                cartProducts[position].per_pack = listPerPackSpinner[pos]
                Handler().post {
                    adapterListener.showCountOnFooter(
                        cartProducts.sumBy { it.per_pack },
                        cartProducts.sumBy { it.count_pack })
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    interface AdapterListener {
        fun onItemClick(id: Int, title: String)
        fun onDeleteButtonClick(listIds: MutableList<Int>)
        fun showCountOnFooter(countPerPack: Int, countPack: Int)
    }
}