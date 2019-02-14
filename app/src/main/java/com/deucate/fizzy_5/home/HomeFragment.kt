package com.deucate.fizzy_5.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.deucate.fizzy_5.R
import com.deucate.fizzy_5.model.Mart
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment(){

    private val data = ArrayList<Mart>()
    private lateinit var adapter: HomeAdapter

    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        adapter = HomeAdapter(data, object : HomeAdapter.OnHomeCardClickListener{
            override fun onClickCard(position: Int) {}

            override fun onClickLike(position: Int, viewHolder: HomeViewHolder) {
                viewHolder.like.setImageResource(R.drawable.ic_liked)
            }
        })

        val recyclerView = rootView.homeRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        db.collection(getString(R.string.vendors)).get().addOnCompleteListener {
            if (it.isSuccessful) {
                val result = it.result!!
                if (result.isEmpty) {
                    rootView.homeNotFoundAnimation.playAnimation()
                    rootView.homeNotFoundAnimation.visibility = View.VISIBLE
                } else {
                    for (mart in result.documents) {
                        data.add(Mart(mart.id, mart.getString("Name")!!, mart.getString("StartTime")!!, mart.getString("CloseTime")!!, mart.getGeoPoint("Location")!!))
                    }
                    adapter.notifyDataSetChanged()
                }
            } else {
                Toast.makeText(activity, it.exception!!.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }

        return rootView
    }

}
