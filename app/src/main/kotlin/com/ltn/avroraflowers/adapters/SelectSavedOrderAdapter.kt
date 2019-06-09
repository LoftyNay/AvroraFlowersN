package com.ltn.avroraflowers.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.model.SavedProduct
import com.ltn.avroraflowers.model.SavedProductKey


class SelectSavedOrderAdapter(private val onClickCardListener: OnCardItemClickListener) :
    RecyclerView.Adapter<SelectSavedOrderAdapter.ViewHolder>() {

    private var savedOrders: MutableMap<SavedProductKey, MutableList<SavedProduct>> = LinkedHashMap()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dialog_select_saved_order_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return savedOrders.size
    }

    fun addAllProducts(list: HashMap<SavedProductKey, MutableList<SavedProduct>>) {
        savedOrders.clear()
        savedOrders.putAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardItem.setOnClickListener {
            //  onClickCardListener.onItemClick
           // savedOrders.entries.iterator().

        }

        //holder.savedOrderName.text = savedOrders[position].name

        /* savedOrders.forEach {
             if (it.save_order_id == savedOrders)
         }
         savedOrders[position].title

         holder.productsInSavedOrder.text =
         holder.countPack.text = savedOrders[position].count_pack.toString()
         holder.color.text = savedOrders[position].color*/
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardItem = itemView.findViewById<MaterialCardView>(R.id.cvSavedOrders)
        val savedOrderName = itemView.findViewById<TextView>(R.id.tvSavedOrderName)
        val productsInSavedOrder = itemView.findViewById<TextView>(R.id.tvProductsInSavedOrder)
    }

    interface OnCardItemClickListener {
        fun onItemClick(saveOrderId: Int)
    }
}
