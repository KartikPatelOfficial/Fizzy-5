package com.deucate.fizzy_5.home.category

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.deucate.fizzy_5.R
import com.deucate.fizzy_5.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_products.*

class ProductsActivity : AppCompatActivity() {

    private val products = ArrayList<Product>()
    private lateinit var adapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        adapter = ProductsAdapter(products, object : ProductsAdapter.OnClick {
            override fun onClickProduct(product: Product) {
                val intent = Intent(this@ProductsActivity, ProductDetailActivity::class.java)
                intent.putExtra("Product", product)
                startActivity(intent)
            }
        })

        val recyclerView = productsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val categoryID = intent.getStringExtra("CategoryID")
        val subCategoryID = intent.getStringExtra("SubCategoryID")
        val martID = intent.getStringExtra("MartID")

        getAllProducts(martID, categoryID, subCategoryID)

    }

    private fun getAllProducts(martID: String, categoryID: String, subCategoryID: String) {
        FirebaseFirestore.getInstance()
                .collection(getString(R.string.vendors)).document(martID)
                .collection(getString(R.string.category)).document(categoryID)
                .collection(getString(R.string.subCategory)).document(subCategoryID).collection(getString(R.string.products))
                .get().addOnCompleteListener {
                    val data = it.result!!
                    for (product in data) {
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
                    adapter.notifyDataSetChanged()
                }
    }
}
