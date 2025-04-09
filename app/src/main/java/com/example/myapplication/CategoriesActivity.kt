package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.MenuItemCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView

class CategoriesActivity : AppCompatActivity() {
    private var toggle: ActionBarDrawerToggle? = null
    private var searchString: String? = ""

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
                R.id.nav_history -> {
                    startActivity(Intent(this@CategoriesActivity, HistoryActivity::class.java))
                }
            }
            drawer?.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        val listView = findViewById<ListView>(R.id.search_list)
        val searchViewItem = menu.findItem(R.id.search_bar)
        val searchView = MenuItemCompat.getActionView(searchViewItem) as SearchView

        if (searchString != ""){
            searchView.isIconified
            searchView.onActionViewExpanded()
            searchView.setQuery(searchString, false)
            searchView.isFocusable = true;
        }

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedName = listView.getItemAtPosition(position).toString()
                for (i in intArrayOf(1,2,3)) {

                }
            }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                listView.visibility = View.VISIBLE
                val rv = findViewById<RecyclerView>(R.id.recycler_list)
                rv.visibility = View.GONE
                if (newText == "") {
                    listView.visibility = View.GONE
                    rv.visibility = View.VISIBLE
                }
                searchString = newText;
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (toggle!!.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("searchString", searchString);
        super.onSaveInstanceState(outState)
    }
}