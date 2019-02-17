package com.deucate.fizzy_5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.deucate.fizzy_5.model.Product
import com.google.firebase.firestore.FirebaseFirestore

class ProductsActivity : AppCompatActivity() {

    private val products = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        val categoryID = intent.getStringExtra("CategoryID")
        val subCategoryID = intent.getStringExtra("SubCategoryID")
       val martID = intent.getStringExtra("MartID")

        FirebaseFirestore.getInstance()
                .collection(getString(R.string.vendors)).document(martID)
                .collection(getString(R.string.category)).document(categoryID)
                .collection(getString(R.string.subCategory)).document(subCategoryID)
                .get().addOnCompleteListener {
                    val product = it.result!!
                    products.add(Product(
                            id = product.id,
                            name = product.getString("Name")!!,
                            rating = product.getLong("Rating")!!,
                            hasDiscount = product.getBoolean("hasDiscount")!!,
                            price = product.getLong("Price")!!,
                            isStock = product.getBoolean("InStock")!!,
                            discountedPrice = product.getLong("DiscountPrice"),
                            description = product.getString("Description")!!,
                            image = product.getString("Image")!!
                    ))
                }

        Log.d("----->", "\n$categoryID\n$subCategoryID\n$martID")


    }
}
