package com.deucate.fizzy_5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class ProductsActivity : AppCompatActivity() {

    private lateinit var martID: String
    private lateinit var categoryID: String
    private lateinit var subCategoryID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        categoryID = intent.getStringExtra("CategoryID")
        subCategoryID = intent.getStringExtra("SubCategoryID")
        martID = intent.getStringExtra("MartID")

        Log.d("----->", "\n$categoryID\n$subCategoryID\n$martID")


    }
}
