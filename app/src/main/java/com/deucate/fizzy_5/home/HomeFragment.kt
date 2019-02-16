package com.deucate.fizzy_5.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.deucate.fizzy_5.R
import com.deucate.fizzy_5.home.category.CategoryActivity
import com.deucate.fizzy_5.model.Mart
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {

    private val marts = ArrayList<Mart>()
    private lateinit var martAdapter: HomeAdapter

    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView = rootView.homeRecyclerView

        martAdapter = HomeAdapter(marts, object : HomeAdapter.OnHomeCardClickListener {
            override fun onClickCard(position: Int, id: String) {
                val intent = Intent(activity,CategoryActivity::class.java)
                intent.putExtra("MartID",id)
                startActivity(intent)
            }

            override fun onClickLike(position: Int, viewHolder: HomeViewHolder) {
                viewHolder.like.setImageResource(R.drawable.ic_liked)
            }
        })

        recyclerView.adapter = martAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        getVendors(rootView)

        return rootView
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
