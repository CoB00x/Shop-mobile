package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class DeveloperActivity : AppCompatActivity() {
    private var toggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val navigationView: NavigationView? = findViewById(R.id.nav_view)
        val drawer: DrawerLayout? = findViewById(R.id.drawer_layout_developer)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer)
        val actionBar = supportActionBar
        actionBar?.title = "О разработчике"

        ////////////////////////////////////////////NavigationBar////////////////////////////////////////////
        toggle = ActionBarDrawerToggle(
            this@DeveloperActivity, drawer, R.string.drawer_open, R.string.drawer_close
        )
        drawer?.addDrawerListener(toggle!!)
        //toggle!!.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        navigationView?.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_change_profile -> {
                    startActivity(Intent(this@DeveloperActivity, ProfileActivity::class.java))
                }
                R.id.nav_about -> {
                    startActivity(Intent(this@DeveloperActivity, AboutActivity::class.java))
                }
                R.id.nav_main -> {
                    startActivity(Intent(this@DeveloperActivity, MainActivity::class.java))
                }
                R.id.nav_cart -> {
                    startActivity(Intent(this@DeveloperActivity, CartActivity::class.java))
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