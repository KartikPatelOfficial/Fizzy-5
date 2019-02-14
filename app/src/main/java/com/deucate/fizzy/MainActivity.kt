package com.deucate.fizzy

import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val currentUser = auth.currentUser!!
        val navigationView = findViewById<NavigationView>(R.id.nav_view)

        val view = navigationView.getHeaderView(0)

        view.findViewById<TextView>(R.id.nav_nameTV).text = currentUser.displayName
        view.findViewById<TextView>(R.id.nav_emailTv).text = currentUser.email
        Picasso.get().load(currentUser.photoUrl).into(view.findViewById<ImageView>(R.id.nav_profilePic))

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_order -> {

            }
            R.id.nav_mart -> {

            }
            R.id.nav_location -> {

            }
            R.id.nav_help -> {

            }
            R.id.nav_contact -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
