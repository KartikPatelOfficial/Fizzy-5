package com.deucate.fizzy_5

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.deucate.fizzy_5.auth.LoginActivity
import com.deucate.fizzy_5.cart.CartActivity
import com.deucate.fizzy_5.home.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val auth = FirebaseAuth.getInstance()
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //SettingUp ViewModel
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        //Updating Navigation Drawer
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


        //Observing Data from ViewModel
        viewModel.actionbarTitle.observe(this, Observer {
            it.let { title ->
                toolbar.title = title
            }
        })

        viewModel.currentFragment.observe(this, Observer {
            it.let { fragment ->
                if (viewModel.currentFragmentID != fragment.id) {
                    this@MainActivity.supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
                }
            }
        })

        viewModel.currentFragment.value = HomeFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.more_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {
            R.id.logout -> {
                auth.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            R.id.myCart -> {
                startActivity(Intent(this, CartActivity::class.java))
            }
        }

        return true
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
            R.id.nav_home -> {
                viewModel.actionbarTitle.value = "Home"
                viewModel.currentFragment.value = HomeFragment()
            }
            R.id.nav_order -> {
                viewModel.actionbarTitle.value = "Order"

            }
            R.id.nav_location -> {
                viewModel.actionbarTitle.value = "Location"

            }
            R.id.nav_help -> {
                viewModel.actionbarTitle.value = "Help"

            }
            R.id.nav_contact -> {
                viewModel.actionbarTitle.value = "Contact Us"

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
