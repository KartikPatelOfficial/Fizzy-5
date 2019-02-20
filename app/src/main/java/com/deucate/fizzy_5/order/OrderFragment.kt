package com.deucate.fizzy_5.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.deucate.fizzy_5.R
import com.deucate.fizzy_5.model.Order
import kotlinx.android.synthetic.main.alert_order_detail.view.*
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.fragment.app.Fragment
import com.deucate.fizzy_5.model.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_order.view.*


class OrderFragment : Fragment() {

    private val orders = ArrayList<Order>()

    private lateinit var adapter: OrderAdapter

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        val rootView = inflater.inflate(R.layout.fragment_order, container, false)

        adapter = OrderAdapter(orders, object : OrderAdapter.OnClick {
            override fun onClickCard(order: Order) {
                val view = LayoutInflater.from(activity).inflate(R.layout.alert_order_detail, null, false)

                view.orderDetailID.text = order.id
                view.orderDetailMartName.text = order.martName
                view.orderDetailStatus.text = adapter.getStatus(order.status)

                val qrgEncoder = QRGEncoder(order.id, null, QRGContents.Type.TEXT, 200).encodeAsBitmap()
                view.orderDetailQRImage.setImageBitmap(qrgEncoder)

                AlertDialog.Builder(context!!).setTitle("Your order").setView(view).setPositiveButton("Ok") { _, _ -> }.show()
            }
        })

        val recyclerView = rootView.orderRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        db.collection(getString(R.string.order)).whereEqualTo("userID", auth.uid).get().addOnCompleteListener {
            if (it.isSuccessful) {
                val result = it.result!!
                if (!result.isEmpty) {
                    for (order in result) {
                        orders.add(Order(
                                id = order.id,
                                paymentMethod = order.getLong("paymentMethod")!!.toInt(),
                                address = order.getString("address")!!,
                                products = order.get("products")!! as ArrayList<Product>,
                                martName = order.getString("martName")!!,
                                martID = order.getString("martID")!!,
                                status = order.getLong("status")!!.toInt(),
                                time = order.getTimestamp("time")!!,
                                userName = order.getString("userName")!!,
                                userID = order.getString("userID")!!,
                                totalAmount = order.getLong("totalAmount")!!
                        ))
                    }
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(context!!, "Result not found", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context!!, "Error: ${it.exception!!.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }



        return rootView
    }
}
