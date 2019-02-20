package com.deucate.fizzy_5.checkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.deucate.fizzy_5.R
import com.deucate.fizzy_5.model.Order
import com.deucate.fizzy_5.model.Product
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_checkout.*

class CheckoutActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private lateinit var products: ArrayList<Product>

    private val isPickUp = MutableLiveData<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val totalAmount = intent.getLongExtra("TotalAmount", 0)
        products = intent.getSerializableExtra("Products") as ArrayList<Product>

        isPickUp.observe(this, Observer {
            if (it) {
                checkoutAddressLine1.isEnabled = false
                checkoutAddressLine2.isEnabled = false
            } else {
                checkoutAddressLine1.isEnabled = true
                checkoutAddressLine2.isEnabled = true
            }
        })

        checkoutPickupCheckBox.setOnCheckedChangeListener { _, isChecked ->
            isPickUp.value = isChecked
        }

        checkoutOrderButton.setOnClickListener {

            val addressLine1 = checkoutAddressLine1.text
            val addressLine2 = checkoutAddressLine2.text

            if (addressLine1.isNullOrEmpty()) {
                checkoutAddressLine1.error = "Please enter your address."
            }
            if (addressLine2.isNullOrEmpty()) {
                checkoutAddressLine2.error = "Please enter your address."
            }

            val address = if (isPickUp.value!!) {
                "Pick up"
            } else {
                "$addressLine1 \n $addressLine2"
            }

            val order = Order(
                    paymentMethod = checkoutSpinner.selectedItemId.toInt(),
                    address = address,
                    products = products,
                    status = 0,
                    time = Timestamp.now(),
                    userName = auth.currentUser!!.displayName!!,
                    userID = auth.uid!!,
                    totalAmount = totalAmount,
                    martID = "0eqYpRrSwk2sKIig7aOE",
                    martName = "Big bazaar"
            )

            db.collection(getString(R.string.order)).add(order).addOnCompleteListener {
                if (it.isSuccessful) {
                    emptyCart()
                    Toast.makeText(this, "Successfully order placed", Toast.LENGTH_SHORT).show()
                } else {
                    AlertDialog.Builder(this).setTitle("Ohh no!!!").setMessage("Please submit this error to fix them as soon as possible : ${it.exception!!.localizedMessage}")
                    finish()
                }
            }
        }

    }

    private fun emptyCart() {
        var productDeleted = products.size
        val orderDB = db.collection(getString(R.string.user)).document(auth.uid!!).collection(getString(R.string.myCart))
        orderDB.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val result = it.result!!
                for (order in result) {
                    orderDB.document(order.id).delete().addOnCompleteListener {
                        productDeleted--
                        if (productDeleted == 0) {
                            finish()
                        }
                    }
                }
                finish()
            } else {
                AlertDialog.Builder(this).setTitle("Ohh no!!!").setMessage("Please submit this error to fix them as soon as possible : ${it.exception!!.localizedMessage}")
                finish()
            }
        }
    }


}
