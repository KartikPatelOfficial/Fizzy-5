package com.deucate.fizzy_5.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deucate.fizzy_5.R
import com.deucate.fizzy_5.model.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_product.view.*

class CartAdapter(private val products: ArrayList<Product>, private val listener: OnClickCart) : RecyclerView.Adapter<CartViewHolder>() {

    interface OnClickCart {
        fun onClickRemove(position: Int)
        fun onClickCard(product: Product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_product,parent,false))
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = products[position]
        holder.title.text = product.name
        holder.rating.rating = (product.rating / 2).toFloat()
        Picasso.get().load(product.image).into(holder.imageView)

        holder.cardView.setOnClickListener {
            listener.onClickCard(product)
        }

        holder.deleteButton.setOnClickListener {
            listener.onClickRemove(position)
        }
    }

}

class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imageView = view.productImageView!!
    val title = view.productCardTitle!!
    val rating = view.productCardRating!!
    val cardView = view.productCard!!
    val deleteButton = view.productCardDelete!!
}