package com.ltn.avroraflowers.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.model.Category
import com.ltn.avroraflowers.model.OrderItem
import com.ltn.avroraflowers.model.Repository.OrdersRepository
import com.squareup.picasso.Picasso

class OrdersAdapter(private val onClickCardListener: OnCardItemClickListener) :
    RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {

    private var ordersList: MutableList<OrderItem> = OrdersRepository.getInstance().getList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.orders_item_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ordersList.size
    }

    fun invalidate() {
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.orderCardItem.setOnClickListener { onClickCardListener.onItemClick(ordersList[position]._id) }
        holder.status.text = ordersList[position].status
        holder.date.text = ordersList[position].date
        holder.price.text = ordersList[position].price
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderCardItem = itemView.findViewById<MaterialCardView>(R.id.ordersCardItem)
        val status = itemView.findViewById<TextView>(R.id.statusOrdersItem)
        val date = itemView.findViewById<TextView>(R.id.dateOrdersItem)
        val price = itemView.findViewById<TextView>(R.id.sumOrdersItem)
    }

    interface OnCardItemClickListener {
        fun onItemClick(id: Int)
    }
}

