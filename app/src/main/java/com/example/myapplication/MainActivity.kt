package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.MenuItemCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private var toggle: ActionBarDrawerToggle? = null
    private var DVDs: List<DVD>? = null
    var names: MutableList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBar = supportActionBar
        actionBar?.title = "Главная"


        val navigationView: NavigationView? = findViewById(R.id.nav_view)
        val drawer: DrawerLayout? = findViewById(R.id.drawer_layout_main)
        ////////////////////////////////////////////NavigationBar////////////////////////////////////////////
        toggle = ActionBarDrawerToggle(
            this@MainActivity, drawer, R.string.drawer_open, R.string.drawer_close
        )
        drawer?.addDrawerListener(toggle!!)
        toggle!!.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)//
        navigationView?.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_change_profile -> {
                    startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
                }

                R.id.nav_about -> {
                    startActivity(Intent(this@MainActivity, AboutActivity::class.java))
                }

                R.id.nav_author -> {
                    startActivity(Intent(this@MainActivity, DeveloperActivity::class.java))
                }

                R.id.nav_cart -> {
                    startActivity(Intent(this@MainActivity, CartActivity::class.java))
                }
            }
            drawer?.closeDrawer(GravityCompat.START)
            true
        }
        DVDs = setDVDs()
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val myAdapter = RecyclerAdapter(DVDs, this@MainActivity)
        recyclerView.adapter = myAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, names!!)
        val listView = findViewById<ListView>(R.id.search_list)
        listView.adapter = adapter
        val searchViewItem = menu.findItem(R.id.search_bar)
        val searchView = MenuItemCompat.getActionView(searchViewItem) as SearchView
        listView.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            val selectedName = listView.getItemAtPosition(position).toString()
            for (i in names!!.indices) {
                Log.i("qwe", DVDs!![0].name)
                if (DVDs!![i].name == selectedName) {
                    Log.i("qwe", "qwe")
                    val intent = Intent(this@MainActivity, ItemActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent.putExtra("DVD", DVDs!![i])
                    startActivity(intent)
                }
            }
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (names!!.contains(query)) {
                    adapter.filter.filter(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                listView.visibility = View.VISIBLE
                val rv = findViewById<RecyclerView>(R.id.recycler_list)
                rv.visibility = View.GONE
                adapter.filter.filter(newText)
                if (newText == "") {
                    listView.visibility = View.GONE
                    rv.visibility = View.VISIBLE
                }
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

    private fun setDVDs(): List<DVD> {
        val list: MutableList<DVD> = ArrayList()
        list.add(DVD("Аладин", R.drawable.aladin, resources.getString(R.string.aladin), 300.0))
        list.add(
            DVD(
                "Ледниковый период",
                R.drawable.iceage,
                resources.getString(R.string.iceage),
                350.0
            )
        )
        list.add(DVD("Сезон охоты", R.drawable.season, resources.getString(R.string.season), 299.0))
        list.add(DVD("Тачки", R.drawable.cars, resources.getString(R.string.cars), 279.0))
        list.add(
            DVD(
                "Хлодное сердце",
                R.drawable.frozen,
                resources.getString(R.string.frozen),
                321.0
            )
        )
        list.add(DVD("Шрек", R.drawable.shrek, resources.getString(R.string.shrek), 333.0))
        names = ArrayList()
        for (i in list.indices) {
            names!!.add(list[i].name)
        }
        return list
    }
}