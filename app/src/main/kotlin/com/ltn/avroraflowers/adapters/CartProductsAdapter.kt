package com.ltn.avroraflowers.adapters

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.util.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.model.CartItem


class CartProductsAdapter(
    private val onClickListener: OnClickListener,
    private val onSelectCheckBoxLitener: OnSelectCheckBoxLitener
) : RecyclerView.Adapter<ViewHolder>() {

    //    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1
    private val TYPE_FOOTER = 2

    private var cartProducts: MutableList<CartItem> = ArrayList()
    private var listCheckedItems = SparseBooleanArray()
    private var isFooterCheckBoxSelected: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        lateinit var itemView: View
        when (viewType) {
            /*     TYPE_HEADER -> {
                     itemView = LayoutInflater.from(parent.context).inflate(R.layout.header_recycler_cart, parent, false)
                     return HeaderViewHolder(itemView)
                 }*/
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

    fun notifySelectedItems(selected: Boolean) {
        setSelectedCheckBox(selected)
        notifyDataSetChanged()
    }

    fun notifyFooter(check: Boolean) {
        isFooterCheckBoxSelected = check
        notifyItemChanged(cartProducts.size)
    }

    fun addAll(cartProducts: List<CartItem>) {
        this.cartProducts.addAll(cartProducts)
        this.cartProducts.reverse()
        for (i in cartProducts.indices)
            listCheckedItems.put(i, false)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
//            is HeaderViewHolder -> {

//            }
            is ItemViewHolder -> {
                holder.title.text = cartProducts[position].title
                holder.colorText.text = cartProducts[position].color
                holder.cartCardItem.setOnClickListener { onClickListener.onItemClick(cartProducts[position]._id) }
                holder.checkItem.isChecked = listCheckedItems[position]
                holder.checkItem.setOnClickListener {
                    listCheckedItems[position] = !listCheckedItems[position]
                    if (listCheckedItems.containsValue(false))
                        onSelectCheckBoxLitener.onSelectedItemCheckBox(false)
                    else
                        onSelectCheckBoxLitener.onSelectedItemCheckBox(true)
                }
                initPerPackSpinner(holder, position)
                initCountPackSpinner(holder, position)
            }
            is FooterViewHolder -> {
                holder.selectAllCheckBox.isChecked = isFooterCheckBoxSelected
                holder.selectAllCheckBox.setOnClickListener {
                    onSelectCheckBoxLitener.onSelectedFooterCheckBox(!isFooterCheckBoxSelected)
                    isFooterCheckBoxSelected = !isFooterCheckBoxSelected
                }
                holder.deleteButton.setOnClickListener {
                    val listIds: MutableList<Int> = ArrayList()
                    for (i in listCheckedItems.keyIterator()) {
                        if (listCheckedItems[i]) {
                            listIds.add(cartProducts[i]._id)
                        }
                    }
                    onClickListener.onDeleteButtonClick(listIds)
                }
            }
        }
    }

    /*  class HeaderViewHolder(itemView: View) : ViewHolder(itemView) {

      }*/

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
            // 0 -> return TYPE_HEADER
            cartProducts.size -> return TYPE_FOOTER
            else -> return TYPE_ITEM;
        }
    }

    private fun setSelectedCheckBox(select: Boolean) {
        for (i in listCheckedItems.keyIterator())
            listCheckedItems.put(i, select)
    }

    private fun initCountPackSpinner(holder: ItemViewHolder, position: Int) {
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

    interface OnSelectCheckBoxLitener {
        fun onSelectedItemCheckBox(selected: Boolean)
        fun onSelectedFooterCheckBox(selected: Boolean)
    }
}