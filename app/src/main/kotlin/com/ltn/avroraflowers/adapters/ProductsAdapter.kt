package com.ltn.avroraflowers.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.model.Product
import com.squareup.picasso.Picasso
import android.widget.AdapterView.OnItemSelectedListener as OnItemSelectedListener1


class ProductsAdapter(
    private val onClickCardListener: OnCardItemClickListener,
    private val onAddToCartClickListener: OnAddToCartClickListener
) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    private var products: MutableList<Product> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun addAll(products: List<Product>) {
        this.products.addAll(products)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardItem.setOnClickListener { onClickCardListener.onItemClick(products[position]._id) }

        Picasso.get()
            .load(products[position].image)
            .fit()
            .centerCrop()
            .into(holder.image)

        holder.title.text = products[position].title
        holder.colorProduct.text = products[position].description.subSequence(0, 10)
        val listSpinner = listOf(200, 400, 600)
        holder.packSpinner.adapter = ArrayAdapter<Int>(
            holder.cardItem.context,
            android.R.layout.simple_spinner_item,
            listSpinner
        )

        var currentSpinnerItem = listSpinner[0]

        holder.packSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentSpinnerItem = listSpinner[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        holder.minusInCardButton.setOnClickListener {
            val count = Integer.decode(holder.countInCard.text.toString())
            minusCount(count, holder)
        }

        holder.plusInCardButton.setOnClickListener {
            val count = Integer.decode(holder.countInCard.text.toString())
            plusCount(count, holder)
        }

        holder.addInCardButton.setOnClickListener {
            onAddToCartClickListener.onAddToCartClick(
                products[position]._id,
                Integer.decode(holder.countInCard.text.toString()),
                currentSpinnerItem
            )
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardItem = itemView.findViewById<MaterialCardView>(R.id.productsCardItem)
        val image = itemView.findViewById<AppCompatImageView>(R.id.imageItemProductRecycler)
        val title = itemView.findViewById<TextView>(R.id.textViewHeadProductsRecycler)
        val colorProduct = itemView.findViewById<TextView>(R.id.textViewColorProductsRecycler)
        val packSpinner = itemView.findViewById<Spinner>(R.id.productsPackSpinner)
        val minusInCardButton = itemView.findViewById<ImageButton>(R.id.productsMinusInCardButton)
        val countInCard = itemView.findViewById<TextView>(R.id.productsCountInCard)
        val plusInCardButton = itemView.findViewById<ImageButton>(R.id.productsPlusInCardButton)
        val addInCardButton = itemView.findViewById<ImageButton>(R.id.productsAddInCardButton)
    }

    private fun minusCount(count: Int, holder: ViewHolder) {
        if (count <= 1) {
            holder.countInCard.isEnabled = false
        } else {
            holder.countInCard.text = (count - 1).toString()
        }
    }

    private fun plusCount(count: Int, holder: ViewHolder) {
        if (count >= 50) {
            holder.countInCard.isEnabled = false
        } else {
            holder.countInCard.text = (count + 1).toString()
        }
    }

    interface OnCardItemClickListener {
        fun onItemClick(id: Int)
    }

    interface OnAddToCartClickListener {
        fun onAddToCartClick(id: Int, count: Int, perPack: Int)
    }
}