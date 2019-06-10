package com.ltn.avroraflowers.adapters

import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.model.SavedProduct
import com.ltn.avroraflowers.model.SavedProductKey
import com.ltn.avroraflowers.ui.base.BaseFragment


class SelectSavedOrderAdapter(
    private val onClickCardListener: OnCardItemClickListener,
    private val onEmptyListener: BaseFragment.EmptyListener
) :
    RecyclerView.Adapter<SelectSavedOrderAdapter.ViewHolder>() {

    private var savedOrders: LinkedHashMap<SavedProductKey, MutableList<SavedProduct>> = LinkedHashMap()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dialog_select_saved_order_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return savedOrders.size
    }

    fun addAllProducts(list: HashMap<SavedProductKey, MutableList<SavedProduct>>) {
        savedOrders.clear()
        savedOrders.putAll(list.toSortedMap(compareByDescending { it.id }))
        notifyDataSetChanged()
    }

    fun invalidate() {
        if (savedOrders.size != 0) {
            onEmptyListener.onHideEmpty()
        } else {
            onEmptyListener.onShowEmpty()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardItem.setOnClickListener {
            onClickCardListener.onItemClick(savedOrders.toList()[position].first.name, savedOrders.toList()[position].first.id)
        }
        holder.savedOrderName.text = savedOrders.toList()[position].first.name
        savedOrders.toList()[position].second.forEach {
            val chip = Chip(holder.chipContainer.context)
            chip.text = it.title
            chip.setTextColor(ContextCompat.getColor(holder.cardItem.context, android.R.color.white))
            chip.isEnabled = false
            chip.isClickable = false
            chip.chipBackgroundColor =
                ColorStateList.valueOf(ContextCompat.getColor(holder.cardItem.context, R.color.chipsColor))
            holder.chipContainer.addView(chip)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardItem = itemView.findViewById<MaterialCardView>(R.id.cvSavedOrders)
        val savedOrderName = itemView.findViewById<TextView>(R.id.tvSavedOrderName)
        val chipContainer = itemView.findViewById<ChipGroup>(R.id.chipContainerSelectSavedOrder)
        //val productsInSavedOrder = itemView.findViewById<TextView>(R.id.tvProductsInSavedOrder)
    }

    interface OnCardItemClickListener {
        fun onItemClick(savedOrderName: String, saveOrderId: Int)
    }
}
