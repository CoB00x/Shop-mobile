package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class SettingsActivity : AppCompatActivity() {
    private var toggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        val actionBar = supportActionBar
        actionBar?.title = "Категории"

        val navigationView: NavigationView? = findViewById(R.id.nav_view)
        val drawer: DrawerLayout? = findViewById(R.id.drawer_layout_categories)

        ////////////////////////////////////////////NavigationBar////////////////////////////////////////////
        toggle = ActionBarDrawerToggle(
            this@SettingsActivity, drawer, R.string.drawer_open, R.string.drawer_close
        )
        drawer?.addDrawerListener(toggle!!)
        toggle!!.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        navigationView?.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_change_profile -> {
                    startActivity(Intent(this@SettingsActivity, ProfileActivity::class.java))
                }
                R.id.nav_cart -> {
                    startActivity(Intent(this@SettingsActivity, CartActivity::class.java))
                }
                R.id.nav_categories -> {
                    startActivity(Intent(this@SettingsActivity, CategoriesActivity::class.java))
                }
                R.id.nav_main -> {
                    startActivity(Intent(this@SettingsActivity, MainActivity::class.java))
                }
            }
            drawer?.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (toggle!!.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }
}