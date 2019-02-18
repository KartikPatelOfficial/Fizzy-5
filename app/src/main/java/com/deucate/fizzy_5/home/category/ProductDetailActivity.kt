package com.deucate.fizzy_5.home.category

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.ActionBar
import android.graphics.Typeface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.deucate.fizzy_5.R
import com.deucate.fizzy_5.model.Product
import com.google.android.material.resources.TextAppearance
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        product = intent.getSerializableExtra("Product") as Product

        productName.text = product.name
        productDescription.text = product.description
        Picasso.get().load(product.image).into(productImage)

        productRating.rating = (product.rating / 2).toFloat()

        productPrice.addView(getPriceLayout(product.price, product.hasDiscount, product.discountedPrice))

        Log.d("----->", product.name)
    }

    @SuppressLint("PrivateResource")
    private fun getPriceLayout(price: Long, hasDiscount: Boolean, discountedPrice: Long?): View {
        val priceTextView = TextView(this)
        priceTextView.text = price.toString()

        if (hasDiscount) {
            val linearLayout = LinearLayout(this)
            linearLayout.orientation = LinearLayout.VERTICAL

            val text = SpannableString(price.toString())
            priceTextView.text = text

            val discountedText = SpannableString(discountedPrice.toString())
            val discountedPriceTextView = TextView(this)
            discountedPriceTextView.text = discountedText
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                priceTextView.setTextAppearance(R.style.TextAppearance_MaterialComponents_Overline)
                discountedPriceTextView.setTextAppearance(R.style.Base_TextAppearance_MaterialComponents_Subtitle2)
            }

            linearLayout.addView(priceTextView, ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT))
            linearLayout.addView(discountedPriceTextView, ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT))
            return linearLayout
        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                priceTextView.setTextAppearance(R.style.Base_TextAppearance_MaterialComponents_Subtitle2)
            }

            return priceTextView
        }
    }
}
