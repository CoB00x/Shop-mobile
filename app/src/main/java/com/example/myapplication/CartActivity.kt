package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader

class CartActivity : AppCompatActivity() {
    private var toggle: ActionBarDrawerToggle? = null
    private var totalCost = 0
    private var DVDs: List<DVD>? = null
    private var profile: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        val actionBar = supportActionBar
        actionBar?.title = "Корзина"

        val navigationView: NavigationView? = findViewById(R.id.nav_view)
        val drawer: DrawerLayout? = findViewById(R.id.drawer_layout_cart)
        ////////////////////////////////////////////NavigationBar////////////////////////////////////////////
        //drawer = findViewById(R.id.drawer_layout_cart)
        toggle = ActionBarDrawerToggle(
            this@CartActivity, drawer, R.string.drawer_open, R.string.drawer_close
        )
        drawer?.addDrawerListener(toggle!!)
        toggle!!.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        navigationView?.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_main -> {
                    startActivity(Intent(this@CartActivity, MainActivity::class.java))
                }

                R.id.nav_change_profile -> {
                    startActivity(Intent(this@CartActivity, ProfileActivity::class.java))
                }
                R.id.nav_categories -> {
                    startActivity(Intent(this@CartActivity, CategoriesActivity::class.java))
                }
                R.id.nav_options -> {
                    startActivity(Intent(this@CartActivity, SettingsActivity::class.java))
                }
            }
            drawer?.closeDrawer(GravityCompat.START)
            true
        }
        DVDs = try {
            cart
        } catch (e: FileNotFoundException) {
            throw RuntimeException(e)
        }
        if (DVDs != null) {
            val recyclerView = findViewById<RecyclerView>(R.id.recycler_list)
            recyclerView.layoutManager = LinearLayoutManager(this)
            val myAdapter = CartAdapter(DVDs!!, this@CartActivity)
            recyclerView.adapter = myAdapter
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (toggle!!.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    @get:Throws(FileNotFoundException::class)
    private val cart: List<DVD>?
        @SuppressLint("SetTextI18n")
        get() {
            val totalTV: TextView? = findViewById(R.id.total)
            val fileName = "profile_file"
            val directory = filesDir
            var file = File(directory, fileName)
            if (file.exists()) {
                try {
                    val fis = openFileInput(fileName)
                    val bytes = ByteArray(fis.available())
                    fis.read(bytes)
                    fis.close()
                    val fileContent = String(bytes)
                    profile = fileContent
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            file = File(filesDir, "$profile.json")
            if (file.length() == 0L || !file.exists()) {
                totalTV?.text = "Корзина пуста"
            } else {
                val fis = FileInputStream(file)
                val isr = InputStreamReader(fis)
                val listType = object : TypeToken<ArrayList<DVD?>?>() {}.type
                DVDs = Gson().fromJson(isr, listType)
                for (i in DVDs?.indices!!) {
                    totalCost = (totalCost + DVDs!![i].price).toInt()
                }
                totalTV?.text = "Итого: " + totalCost + "Р"
                return DVDs
            }
            return null
        } /*public void delete() throws FileNotFoundException {
        Gson gson = new Gson();
        File file = new File(getFilesDir(), profile+".json");
        List<DVD> dvds;

        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis);
        Type listType = new TypeToken<ArrayList<DVD>>() {}.getType();
        dvds = new Gson().fromJson(isr, listType);



        String json = gson.toJson(dvds);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(json.getBytes());
            fos.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }*/
}