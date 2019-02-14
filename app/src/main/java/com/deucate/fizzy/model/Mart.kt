package com.deucate.fizzy.model

import com.google.firebase.firestore.GeoPoint

data class Mart(
        val name:String,
        val startTime:String,
        val closeTime:String,
        val location:GeoPoint
)