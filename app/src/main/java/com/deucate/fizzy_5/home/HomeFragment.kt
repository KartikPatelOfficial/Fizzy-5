package com.deucate.fizzy_5.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.deucate.fizzy_5.R
import com.deucate.fizzy_5.home.category.HomeCategoryAdapter
import com.deucate.fizzy_5.model.Category
import com.deucate.fizzy_5.model.Mart
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {

    private val marts = ArrayList<Mart>()
    private lateinit var martAdapter: HomeAdapter

    private val categories = ArrayList<Category>()
    private lateinit var categoryAdapter: HomeCategoryAdapter

    private val db = FirebaseFirestore.getInstance()
    private var isCategory = true

    private var martID = "0eqYpRrSwk2sKIig7aOE"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView = rootView.homeRecyclerView

        martAdapter = HomeAdapter(marts, object : HomeAdapter.OnHomeCardClickListener {
            override fun onClickCard(position: Int, id: String) {
                getCategory(rootView, id)
                recyclerView.adapter = categoryAdapter
                martID = id
            }

            override fun onClickLike(position: Int, viewHolder: HomeViewHolder) {
                viewHolder.like.setImageResource(R.drawable.ic_liked)
            }
        })

        categoryAdapter = HomeCategoryAdapter(categories, object : HomeCategoryAdapter.OnClickCardCategory {
            override fun onClickCard(position: Int, category: Category) {
                if (isCategory) {
                    getSubcategory(rootView, category.id)
                    isCategory = false
                } else {

                }
            }
        })

        recyclerView.adapter = martAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        getVendors(rootView)

        return rootView
    }

    private fun getCategory(rootView: View, id: String) {
        db.collection(getString(R.string.vendors)).document(id).collection(getString(R.string.category)).get().addOnCompleteListener {
            if (it.isSuccessful) {
                val result = it.result!!
                if (result.isEmpty) {
                    rootView.homeNotFoundAnimation.playAnimation()
                    rootView.homeNotFoundAnimation.visibility = View.VISIBLE
                } else {
                    for (category in result.documents) {
                        categories.add(Category(
                                category.id,
                                category.getString("Name")!!,
                                category.getString("Picture")!!
                        ))
                    }
                    categoryAdapter.notifyDataSetChanged()
                }
            } else {
                Toast.makeText(activity, it.exception!!.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getSubcategory(rootView: View, id: String) {
        db.collection(getString(R.string.vendors)).document(martID).collection(getString(R.string.category)).document(id).collection(getString(R.string.subCategory)).get().addOnCompleteListener {
            if (it.isSuccessful) {
                val result = it.result!!
                if (result.isEmpty) {
                    rootView.homeNotFoundAnimation.playAnimation()
                    rootView.homeNotFoundAnimation.visibility = View.VISIBLE
                } else {
                    categories.clear()
                    for (category in result.documents) {
                        categories.add(Category(
                                category.id,
                                category.getString("Name")!!,
                                category.getString("Image")!!
                        ))
                    }
                    categoryAdapter.notifyDataSetChanged()
                }
            } else {
                Toast.makeText(activity, it.exception!!.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getVendors(rootView: View) {
        db.collection(getString(R.string.vendors)).get().addOnCompleteListener {
            if (it.isSuccessful) {
                val result = it.result!!
                if (result.isEmpty) {
                    rootView.homeNotFoundAnimation.playAnimation()
                    rootView.homeNotFoundAnimation.visibility = View.VISIBLE
                } else {
                    for (mart in result.documents) {
                        marts.add(Mart(mart.id, mart.getString("Name")!!, mart.getString("StartTime")!!, mart.getString("CloseTime")!!, mart.getGeoPoint("Location")!!))
                    }
                    martAdapter.notifyDataSetChanged()
                }
            } else {
                Toast.makeText(activity, it.exception!!.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

}
