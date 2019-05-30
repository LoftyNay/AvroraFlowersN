package com.ltn.avroraflowers.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.model.OrderInner

class InnerOrderAdapter(private val onClickCardListener: OnCardItemClickListener) :
    RecyclerView.Adapter<InnerOrderAdapter.ViewHolder>() {

    private var ordersInnerList: MutableList<OrderInner> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.inner_orders_item_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ordersInnerList.size
    }

    fun addAllProducts(list: List<OrderInner>) {
        ordersInnerList.clear()
        ordersInnerList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardItem.setOnClickListener { onClickCardListener.onItemClick(ordersInnerList[position].product_id, ordersInnerList[position].title) }
        holder.title.text = ordersInnerList[position].title
        holder.perPack.text = ordersInnerList[position].per_pack.toString()
        holder.countPack.text = ordersInnerList[position].count_pack.toString()
        holder.color.text = ordersInnerList[position].color
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardItem = itemView.findViewById<MaterialCardView>(R.id.innerOrderCardItem)
        val title = itemView.findViewById<TextView>(R.id.titleProductInnerOrder)
        val color = itemView.findViewById<TextView>(R.id.colorProductInnerOrder)
        val perPack = itemView.findViewById<TextView>(R.id.perPackInnerOrder)
        val countPack = itemView.findViewById<TextView>(R.id.countPackInnerOrder)
    }

    interface OnCardItemClickListener {
        fun onItemClick(id: Int, title: String)
    }
}
