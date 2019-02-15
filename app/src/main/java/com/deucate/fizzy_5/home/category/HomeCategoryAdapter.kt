package com.deucate.fizzy_5.home.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deucate.fizzy_5.R
import com.deucate.fizzy_5.model.Category
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_home_category.view.*

class HomeCategoryAdapter(private val categories: ArrayList<Category>, private val listener: OnClickCardCategory) : RecyclerView.Adapter<HomeCategoryViewHolder>() {

    interface OnClickCardCategory {
        fun onClickCard(position: Int, category: Category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCategoryViewHolder {
        return HomeCategoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_home_category, parent, false))
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: HomeCategoryViewHolder, position: Int) {
        val category = categories[position]

        Picasso.get().load(category.url).into(holder.image)
        holder.name.text = category.name
        holder.layout.setOnClickListener {
            listener.onClickCard(position, category)
        }
    }
}

class HomeCategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val layout = view.homeCardCategoryView!!
    val image = view.homeCardCategoryImage!!
    val name = view.homeCardCategoryName!!
}