package com.ltn.avroraflowers.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.core.widget.ImageViewCompat
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.model.Category

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    private var categoriesList: MutableList<Category> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    fun addCategory(category: Category) {
        categoriesList.add(category)
        notifyDataSetChanged()
    }

    fun addAll(categories: List<Category>) {
        categoriesList.addAll(categories)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.imageCategory.setImageDrawable(R.dr)
        holder.titleCategory.text = categoriesList[position].title
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageCategory = itemView.findViewById<AppCompatImageView>(R.id.imageItemCategoryRecycler)
        val titleCategory = itemView.findViewById<TextView>(R.id.textViewItemCategoryRecycler)
    }
}