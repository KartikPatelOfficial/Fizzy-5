package com.deucate.fizzy_5.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.deucate.fizzy_5.R
import kotlinx.android.synthetic.main.activity_order.*

class OrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val recyclerView = orderRecyclerView


    }
}
