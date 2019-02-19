package com.deucate.fizzy_5.order

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deucate.fizzy_5.R
import com.deucate.fizzy_5.model.Order
import kotlinx.android.synthetic.main.card_order.view.*
import java.text.SimpleDateFormat

class OrderAdapter(private val orders: ArrayList<Order>, private val listener: OnClick) : RecyclerView.Adapter<OrderViewHolder>() {

    interface OnClick {
        fun onClickCard(order: Order)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_order, parent, false))
    }

    override fun getItemCount(): Int {
        return orders.size

    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]

        holder.cardView.setOnClickListener {
            listener.onClickCard(order)
        }

        holder.nameTextView.text = order.martName
        holder.statusTextView.text = getStatus(order.status)
        holder.timeTextView.text = SimpleDateFormat("DD/mm/YYYY hh:mm aa").format(order.time.toDate()).toString()
        holder.priceTextView.text = order.totalAmount.toString()

    }

    fun getStatus(status: Int): String {
        return when (status) {
            0 -> "Placed"
            1 -> "Waiting"
            2 -> "Dispatching"
            3 -> "Ready"
            4 -> "Done"
            else -> "Unknown"
        }
    }

}

class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val cardView = view.orderCard!!
    val nameTextView = view.cardOrderMartName!!
    val statusTextView = view.cardOrderStatus!!
    val timeTextView = view.cardOrderTime!!
    val priceTextView = view.cardOrderTotalAmount!!
}