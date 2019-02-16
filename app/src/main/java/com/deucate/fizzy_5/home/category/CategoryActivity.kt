package com.deucate.fizzy_5.home.category

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.deucate.fizzy_5.ProductsActivity
import com.deucate.fizzy_5.R
import com.deucate.fizzy_5.model.Category
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_category.*

class CategoryActivity : AppCompatActivity() {

    private val categories = ArrayList<Category>()
    private lateinit var adapter: HomeCategoryAdapter

    private var isSubcategory = false

    private lateinit var martID: String
    private lateinit var nextIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        nextIntent = Intent(this@CategoryActivity, ProductsActivity::class.java)

        martID = intent.getStringExtra("MartID")

        adapter = HomeCategoryAdapter(
                categories = categories,
                listener = object : HomeCategoryAdapter.OnClickCardCategory {
                    override fun onClickCard(position: Int, category: Category) {
                        if (isSubcategory) {
                            nextIntent.putExtra("SubCategoryID", category.id)
                            nextIntent.putExtra("MartID", martID)
                            startActivity(nextIntent)
                        } else {
                            getSubCategory(category.id)
                            nextIntent.putExtra("CategoryID", category.id)
                            isSubcategory = true
                        }
                    }
                }
        )

        val recyclerView = categoryRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        FirebaseFirestore.getInstance().collection(getString(R.string.vendors)).document(martID).collection(getString(R.string.category)).get().addOnCompleteListener {
            if (it.isSuccessful) {
                val result = it.result
                if (!result!!.isEmpty) {
                    categories.clear()
                    for (category in result.documents) {
                        categories.add(Category(
                                id = category.id,
                                name = category.getString("Name")!!,
                                url = category.getString("Picture")!!
                        ))
                    }
                    adapter.notifyDataSetChanged()
                }
            } else {
                AlertDialog.Builder(this).setTitle("Error").setMessage(it.exception!!.localizedMessage).show()
            }
        }

    }

    private fun getSubCategory(id: String) {
        FirebaseFirestore.getInstance().collection(getString(R.string.vendors)).document(martID).collection(getString(R.string.category)).document(id).collection(getString(R.string.subCategory)).get().addOnCompleteListener {
            if (it.isSuccessful) {
                val result = it.result
                if (!result!!.isEmpty) {
                    categories.clear()
                    for (category in result.documents) {
                        categories.add(Category(
                                id = category.id,
                                name = category.getString("Name")!!,
                                url = category.getString("Image")!!
                        ))
                    }
                    adapter.notifyDataSetChanged()
                }
            } else {
                AlertDialog.Builder(this).setTitle("Error").setMessage(it.exception!!.localizedMessage).show()
            }
        }
    }
}
