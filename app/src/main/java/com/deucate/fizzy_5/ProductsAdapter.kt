package com.deucate.fizzy_5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deucate.fizzy_5.model.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_product.view.*

class ProductsAdapter(private val products: ArrayList<Product>) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_product, parent, false))
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.title.text = product.name
        holder.rating.rating = (product.rating / 2).toFloat()
        Picasso.get().load(product.image).into(holder.imageView)
    }

}

class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imageView = view.productImageView!!
    val title = view.productCardTitle!!
    val rating = view.productCardRating!!
}