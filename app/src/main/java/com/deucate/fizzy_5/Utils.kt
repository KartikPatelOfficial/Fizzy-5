package com.deucate.fizzy_5

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.text.SpannableString
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

class Utils(val context: Context) {

    @SuppressLint("PrivateResource")
    fun getPriceLayout(price: Long, hasDiscount: Boolean, discountedPrice: Long?): View {
        val priceTextView = TextView(context)
        priceTextView.text = price.toString()

        if (hasDiscount) {
            val linearLayout = LinearLayout(context)
            linearLayout.orientation = LinearLayout.VERTICAL

            val text = SpannableString(price.toString())
            priceTextView.text = text

            val discountedText = SpannableString(discountedPrice.toString())
            val discountedPriceTextView = TextView(context)
            discountedPriceTextView.text = discountedText
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                priceTextView.setTextAppearance(com.deucate.fizzy_5.R.style.TextAppearance_MaterialComponents_Overline)
                discountedPriceTextView.setTextAppearance(R.style.TextAppearance_MaterialComponents_Subtitle2)
            }

            linearLayout.addView(priceTextView, ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT))
            linearLayout.addView(discountedPriceTextView, ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT))
            return linearLayout
        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                priceTextView.setTextAppearance(R.style.TextAppearance_MaterialComponents_Subtitle2)
            }

            return priceTextView
        }
    }
}