package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class CategoriesActivity : AppCompatActivity() {
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
            this@CategoriesActivity, drawer, R.string.drawer_open, R.string.drawer_close
        )
        drawer?.addDrawerListener(toggle!!)
        toggle!!.syncState()
        if (supportActionBar == null){
            Log.i("qwe","qwe")
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        navigationView?.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_change_profile -> {
                    startActivity(Intent(this@CategoriesActivity, ProfileActivity::class.java))
                }
                R.id.nav_cart -> {
                    startActivity(Intent(this@CategoriesActivity, CartActivity::class.java))
                }
                R.id.nav_main -> {
                    startActivity(Intent(this@CategoriesActivity, MainActivity::class.java))
                }
                R.id.nav_options -> {
                    startActivity(Intent(this@CategoriesActivity, SettingsActivity::class.java))
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