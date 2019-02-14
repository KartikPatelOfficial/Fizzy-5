package com.deucate.fizzy_5.auth

import java.net.URI

data class User(
        val Name: String,
        val Email: String,
        val ProfilePictureURL: URI?
)