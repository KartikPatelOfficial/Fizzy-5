package com.deucate.fizzy_5.model

import com.google.firebase.firestore.GeoPoint

data class Mart(
        val id:String,
        val name:String,
        val startTime:String,
        val closeTime:String,
        val location:GeoPoint
)