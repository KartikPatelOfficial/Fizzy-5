package com.deucate.fizzy_5.home.category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.deucate.fizzy_5.R
import com.google.firebase.firestore.FirebaseFirestore

class CategoryIntent : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_intent)

        val martID = intent.getStringExtra("MartID")

        FirebaseFirestore.getInstance().collection(getString(R.string.vendors)).document(martID).collection(getString(R.string.category)).get().addOnCompleteListener {
            if (it.isSuccessful){
                val result = it.result
                if (result!!.isEmpty){
                    for(category in result.documents){

                    }
                }
            }else{
                AlertDialog.Builder(this).setTitle("Error").setMessage(it.exception!!.localizedMessage).show()
            }
        }

    }
}
