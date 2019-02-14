package com.deucate.fizzy.auth

import java.net.URI

data class User(
        val Name: String,
        val Email: String,
        val ProfilePictureURL: URI?
)