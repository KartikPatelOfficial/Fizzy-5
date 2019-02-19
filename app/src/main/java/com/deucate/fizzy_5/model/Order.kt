package com.deucate.fizzy_5.model

import com.google.firebase.Timestamp

data class Order(
        val id: String = "",
        val paymentMethod: Int, //Spinner
        val address: String, //EditText
        val products: ArrayList<Product>,
        val martName: String,
        val martID: String,
        val status: Int,
        val time: Timestamp,
        val userName: String,
        val userID: String,
        val totalAmount:Long
)