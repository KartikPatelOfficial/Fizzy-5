package com.deucate.fizzy_5.model

data class Product(
        val id: String,
        val name: String,
        val rating: Long,
        val hasDiscount: Boolean,
        val price: Long,
        val isStock: Boolean,
        val discountedPrice: Long?,
        val description: String,
        val image: String
)