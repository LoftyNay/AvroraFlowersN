package com.ltn.avroraflowers.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class CartProductsAdapter(private val onClickListener: OnClickListener) : RecyclerView.Adapter<ViewHolder>() {

    companion object {
        val INVALIDATE_TYPE_DELETE = 0
        val INVALIDATE_TYPE_ADD = 1
    }

    private val TYPE_ITEM = 1
    private val TYPE_FOOTER = 2

    private var cartProducts: MutableList<CartItem> = CartProductsRepository.getInstance().getList()

    private var isFooterCheckBoxSelected: Boolean = false

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

    override fun getItemCount(): Int {
        return cartProducts.size + 1
    }

    private fun notifySelectedItems(selected: Boolean) {
        cartProducts.forEach { it.checked = selected }
        notifyDataSetChanged()
    }

    private fun notifyFooter() {
        val item = cartProducts.find { item -> !item.checked }
        isFooterCheckBoxSelected = item == null
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
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> {
                holder.title.text = cartProducts[position].title
                holder.colorText.text = cartProducts[position].color
                holder.cartCardItem.setOnClickListener { onClickListener.onItemClick(cartProducts[position]._id) }
                holder.checkItem.isChecked = cartProducts[position].checked
                holder.checkItem.setOnClickListener {
                    cartProducts[position].checked = !cartProducts[position].checked
                    notifyFooter()
                }
                initPerPackSpinner(holder, position)
                initCountPackSpinner(holder)
            }
            is FooterViewHolder -> {
                holder.selectAllCheckBox.isChecked = isFooterCheckBoxSelected
                holder.selectAllCheckBox.setOnClickListener {
                    notifySelectedItems(!isFooterCheckBoxSelected)
                    isFooterCheckBoxSelected = !isFooterCheckBoxSelected
                }
                holder.deleteButton.setOnClickListener {
                    Observable.fromIterable(cartProducts)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter { item -> item.checked }
                        .concatMapIterable { item -> mutableListOf(item._id) }
                        .toList()
                        .subscribe { result ->
                            onClickListener.onDeleteButtonClick(result)
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
        val textStatus = itemView.findViewById<TextView>(R.id.textFooterCart)
        val deleteButton = itemView.findViewById<MaterialButton>(R.id.deleteItemsFooterCartButton)
    }

    override fun getItemViewType(position: Int): Int {
        when (position) {
            cartProducts.size -> return TYPE_FOOTER
            else -> return TYPE_ITEM;
        }
    }

    private fun initCountPackSpinner(holder: ItemViewHolder) {
        val listCountSpinner = listOf(1, 2, 3)
        val arrayAdapter = ArrayAdapter<Int>(
            holder.cartCardItem.context,
            android.R.layout.simple_spinner_item,
            listCountSpinner
        )
        holder.countPackSpinner.adapter = arrayAdapter
        for (item in listCountSpinner) {
            holder.countPackSpinner.setSelection(listCountSpinner.indexOf(item))
        }
    }

    private fun initPerPackSpinner(holder: ItemViewHolder, position: Int) {
        val listPerPackSpinner = listOf(200, 400, 600)
        holder.perPackSpinner.adapter = ArrayAdapter<Int>(
            holder.cartCardItem.context,
            android.R.layout.simple_spinner_item,
            listPerPackSpinner
        )
        for (item in listPerPackSpinner) {
            if (item == cartProducts[position].per_pack) {
                holder.perPackSpinner.setSelection(listPerPackSpinner.indexOf(item))
            }
        }
    }

    interface OnClickListener {
        fun onItemClick(id: Int)
        fun onDeleteButtonClick(listIds: MutableList<Int>)
    }
}