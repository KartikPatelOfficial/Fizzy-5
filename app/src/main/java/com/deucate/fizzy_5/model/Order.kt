package com.deucate.fizzy_5.model

import com.google.firebase.Timestamp

data class Order(
        val id: String = "",
        val paymentMethod: Int,
        val address: String,
        val products: ArrayList<Product>,
        val martName: String, //1
        val martID: String,
        val status: Int, //4
        val time: Timestamp, //2
        val userName: String,
        val userID: String,
        val totalAmount:Long //3
)