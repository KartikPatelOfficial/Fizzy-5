package com.deucate.fizzy_5.home.category

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.deucate.fizzy_5.R
import com.deucate.fizzy_5.Utils
import com.deucate.fizzy_5.model.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var product: Product
    private val auth = FirebaseAuth.getInstance()

    private lateinit var utils: Utils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        utils = Utils(this)

        product = intent.getSerializableExtra("Product") as Product

        productName.text = product.name
        productDescription.text = product.description
        Picasso.get().load(product.image).into(productImage)

        productRating.rating = (product.rating / 2).toFloat()

        productPrice.addView(utils.getPriceLayout(product.price, product.hasDiscount, product.discountedPrice))

        productBuyBtn.setOnClickListener {
            /**AlertDialog.Builder(this)
            .setTitle("Please enter your detail")
            .setView(LayoutInflater.from(this).inflate(R.layout.alert_get_information, null))
            .setPositiveButton("Next") { _, _ ->

            }.show()**/

            addToCart(product)
        }

        Log.d("----->", product.name)
    }

    private fun addToCart(product: Product) {
        FirebaseFirestore.getInstance().collection(getString(R.string.user)).document(auth.uid!!).collection(getString(R.string.myCart)).add(product).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Product added to cart", Toast.LENGTH_SHORT).show()
            } else {
                AlertDialog.Builder(this).setTitle("Ohh no!!!").setMessage("Please submit this error to fix them as soon as possible : ${it.exception!!.localizedMessage}")
            }
        }
    }


}
