package com.ltn.avroraflowers.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.ltn.avroraflowers.R
import com.ltn.avroraflowers.model.Product
import com.ltn.avroraflowers.utils.Constants
import com.squareup.picasso.Picasso
import android.widget.AdapterView.OnItemSelectedListener as OnItemSelectedListener1


class ProductsAdapter(
    private val onClickCardListener: OnCardItemClickListener,
    private val onAddToCartClickListener: OnAddToCartClickListener
) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    private var products: MutableList<Product> = ArrayList()
    private var userLogin = false
    private lateinit var parentContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        parentContext = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun addAll(products: List<Product>) {
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()
    }

    fun invalidate(userIsLogin: Boolean) {
        userLogin = userIsLogin
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardItem.setOnClickListener {
            onClickCardListener.onItemClick(
                products[position]._id,
                products[position].title
            )
        }

        Picasso.get()
            .load(products[position].image)
            .fit()
            .centerCrop()
            .into(holder.image)

        holder.title.text = products[position].title
        holder.colorProduct.text = products[position].color
        val listSpinner = Constants.LIST_PER_PACK
        holder.packSpinner.adapter = ArrayAdapter<Int>(
            holder.cardItem.context,
            R.layout.spinner_row,
            listSpinner
        )

        var currentSpinnerItem = listSpinner[0]

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            holder.packSpinner.background = holder.packSpinner.context.getDrawable(R.drawable.spinner_outline_no_arrow)
        }
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

        if (userLogin) {
            holder.addInCardButton.setOnClickListener {
                onAddToCartClickListener.onAddToCartClick(
                    products[position]._id,
                    Integer.decode(holder.countInCard.text.toString()),
                    currentSpinnerItem
                )
            }
        } else {
            holder.addInCardButton.setOnClickListener {
                Toast.makeText(
                    parentContext,
                    "Выполните вход в свой профиль",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPack = itemView.findViewById<TextView>(R.id.textViewPackProductsRecycler)
        val tvOrder = itemView.findViewById<TextView>(R.id.textViewOrderProductsRecycler)
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
        if (count >= Constants.COUNT_PACK_MAX) {
            holder.countInCard.isEnabled = false
        } else {
            holder.countInCard.text = (count + 1).toString()
        }
    }

    interface OnCardItemClickListener {
        fun onItemClick(id: Int, title: String)
    }

    interface OnAddToCartClickListener {
        fun onAddToCartClick(id: Int, count: Int, perPack: Int)
    }
}