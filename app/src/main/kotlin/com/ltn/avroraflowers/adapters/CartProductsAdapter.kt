package com.ltn.avroraflowers.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.model.CartItem

class CartProductsAdapter(private val onClickCardListener: OnCardItemClickListener) :
    RecyclerView.Adapter<CartProductsAdapter.ViewHolder>() {

    private val TYPE_ITEM = 0
    private val TYPE_FOOTER = 1

    private var cartProducts: MutableList<CartItem> = ArrayList()
    private lateinit var listCheckedItems: BooleanArray

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cartProducts.size
    }

    fun getCheckedItemsSize(): Int {
        return listCheckedItems.size
    }

    fun getCheckedItems(): BooleanArray {
        return listCheckedItems
    }

    fun addAll(cartProducts: List<CartItem>) {
        this.cartProducts.addAll(cartProducts)
        listCheckedItems = BooleanArray(cartProducts.size)
//fixme
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = cartProducts[position].title
        holder.colorText.text = cartProducts[position].color
        holder.cartCardItem.setOnClickListener { onClickCardListener.onItemClick(cartProducts[position]._id) }
        holder.checkItem.setOnClickListener {
            listCheckedItems[position] = !listCheckedItems[position]
            if (listCheckedItems.contains(true)) {
                onClickCardListener.onItemCheck(true)
            } else {
                onClickCardListener.onItemCheck(false)
            }
        }
        initPerPackSpinner(holder, position)
        initCountPackSpinner(holder, position)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cartCardItem = itemView.findViewById<MaterialCardView>(R.id.cartCardItem)
        val title = itemView.findViewById<TextView>(R.id.titleProductCart)
        val colorText = itemView.findViewById<TextView>(R.id.colorProductCart)
        val checkItem = itemView.findViewById<AppCompatCheckBox>(R.id.checkBoxItemCart)
        val perPackSpinner = itemView.findViewById<Spinner>(R.id.cartPackSpinner)
        val countPackSpinner = itemView.findViewById<Spinner>(R.id.countPackSpinner)
    }

    private fun initCountPackSpinner(holder: ViewHolder, position: Int) {
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

    private fun initPerPackSpinner(holder: ViewHolder, position: Int) {
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

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    interface OnCardItemClickListener {
        fun onItemClick(id: Int)
        fun onItemCheck(checked: Boolean)
    }
}