package com.deucate.fizzy_5.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.deucate.fizzy_5.checkout.CheckoutActivity
import com.deucate.fizzy_5.R
import com.deucate.fizzy_5.home.category.ProductsActivity
import com.deucate.fizzy_5.model.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val products = ArrayList<Product>()
    private lateinit var adapter: CartAdapter

    private val totalAmount = MutableLiveData<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val recyclerView = cartRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        totalAmount.value = 0

        adapter = CartAdapter(products, object : CartAdapter.OnClickCart {

            override fun onClickRemove(position: Int) {
                products.removeAt(position)
                adapter.notifyItemRemoved(position)
            }

            override fun onClickCard(product: Product) {
                val intent = Intent(this@CartActivity, ProductsActivity::class.java)
                intent.putExtra("Product", product)
                startActivity(intent)
            }

        })
        recyclerView.adapter = adapter

        getProducts()

        totalAmount.observe(this, Observer {
            it.let { amount ->
                cartTotalAmount.text = amount.toString()
            }
        })

        checkoutButton.setOnClickListener {
            val intent = Intent(this, CheckoutActivity::class.java)
            intent.putExtra("Products", products)
            intent.putExtra("TotalAmount", totalAmount.value!!)
            startActivity(intent)
        }

    }

    private fun getProducts() {
        db.collection(getString(R.string.user)).document(auth.uid!!).collection(getString(R.string.myCart)).get().addOnCompleteListener {
            if (it.isSuccessful) {
                val result = it.result!!
                if (!result.isEmpty) {
                    for (product in result) {
                        val price = product.getLong("price")!!
                        products.add(Product(
                                id = product.id,
                                name = product.getString("name")!!,
                                rating = product.getLong("rating")!!,
                                hasDiscount = product.getBoolean("hasDiscount")!!,
                                price = price,
                                isStock = product.getBoolean("stock")!!,
                                discountedPrice = product.getLong("discountedPrice")!!,
                                description = product.getString("description")!!,
                                image = product.getString("image")!!
                        ))
                        totalAmount.value = totalAmount.value?.plus(price.toInt())
                    }
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show()
                }
            } else {
                AlertDialog.Builder(this).setTitle("Ohh no!!!").setMessage("Please submit this error to fix them as soon as possible : ${it.exception!!.localizedMessage}")
            }
        }
    }
}
