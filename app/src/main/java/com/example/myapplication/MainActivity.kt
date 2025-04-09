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
import com.google.android.material.switchmaterial.SwitchMaterial

const val EXAMPLE_PREFERENCES = "example_preferences"
const val EDIT_TEXT_KEY = "key_for_edit_text"

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private var toggle: ActionBarDrawerToggle? = null
    private var DVDs: List<DVD>? = null
    var names: MutableList<String>? = null
    private var searchString: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPrefs = getSharedPreferences(EXAMPLE_PREFERENCES, MODE_PRIVATE)

        if (savedInstanceState!=null){
            searchString = savedInstanceState.getString("searchString")
        }

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
                R.id.nav_cart -> {
                    startActivity(Intent(this@MainActivity, CartActivity::class.java))
                }
                R.id.nav_categories -> {
                    startActivity(Intent(this@MainActivity, CategoriesActivity::class.java))
                }
/*                R.id.nav_options -> {
                    startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
                }*/
                R.id.nav_history -> {
                    startActivity(Intent(this@MainActivity, HistoryActivity::class.java))
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
        searchString?.let { Log.i("qwe", it) };
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, names!!)
        val listView = findViewById<ListView>(R.id.search_list)
        listView.adapter = adapter
        val searchViewItem = menu.findItem(R.id.search_bar)
        val searchView = MenuItemCompat.getActionView(searchViewItem) as SearchView

        if (searchString != ""){
            searchView.isIconified
            searchView.onActionViewExpanded()
            searchView.setQuery(searchString, false)
            searchView.isFocusable = true;
        }

        listView.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            val selectedName = listView.getItemAtPosition(position).toString()
            for (i in names!!.indices) {
                if (DVDs!![i].name == selectedName) {
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

    private fun setDVDs(): List<DVD> {
        val list: MutableList<DVD> = ArrayList()
        list.add(DVD("Диван Парма", R.drawable.parma, "Диван-кровать Парма выполнен в строгом минималистичном стиле. Все его поверхности имеют правильную геометрическую форму. Лаконичный и элегантный дизайн визуально не перегружает пространство, а пастельные тона обивки будут гармонично сочетаться с широкой гаммой других оттенков в интерьере. Эта модель доступна только в тех вариантах обивки, которые представлены в нашем ассортименте.", 300.0))
        list.add(DVD("Диван Пекин",
            R.drawable.pekin, "Диван-кровать Пекин — элегантная и практичная модель с узкими и при этом удобными подлокотниками, которая понравится обладателям небольших комнат. При ширине дивана 200 см спальное место лишь немногим уже — 192 см, поэтому одному человеку спать на нем будет комфортно и без раскладывания. Благодаря велюровой обивке и отстрочке модель выглядит стильно и эффектно.", 333.0))
        names = ArrayList()
        for (i in list.indices) {
            names!!.add(list[i].name)
        }
        return list
    }
}