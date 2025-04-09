@file:Suppress("DEPRECATION")

package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.switchmaterial.SwitchMaterial
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ProfileActivity : AppCompatActivity() {
    private var toggle: ActionBarDrawerToggle? = null
    private var profile: String? = "Войдите, чтобы продолжить"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val btn: Button = findViewById(R.id.btn_profile)
        val out: Button? = findViewById(R.id.btn_out)
        setProfile()
        val actionBar = supportActionBar
        actionBar?.title = "Профиль"

        val navigationView: NavigationView? = findViewById(R.id.nav_view)
        val drawer: DrawerLayout? = findViewById(R.id.drawer_layout_profile)

        ////////////////////////////////////////////NavigationBar////////////////////////////////////////////
        toggle = ActionBarDrawerToggle(
            this@ProfileActivity, drawer, R.string.drawer_open, R.string.drawer_close
        )
        drawer?.addDrawerListener(toggle!!)
        toggle!!.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        navigationView?.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_main -> {
                    startActivity(Intent(this@ProfileActivity, MainActivity::class.java))
                }
                R.id.nav_cart -> {
                    startActivity(Intent(this@ProfileActivity, CartActivity::class.java))
                }
                R.id.nav_categories -> {
                    startActivity(Intent(this@ProfileActivity, CategoriesActivity::class.java))
                }
/*                R.id.nav_options -> {
                    startActivity(Intent(this@ProfileActivity, SettingsActivity::class.java))
                }*/
                R.id.nav_history -> {
                    startActivity(Intent(this@ProfileActivity, HistoryActivity::class.java))
                }
            }
            drawer?.closeDrawer(GravityCompat.START)
            true
        }
        btn.setOnClickListener {
            Log.i("qwe","qwe");
            val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivityForResult(intent, 1)
        }
        out?.setOnClickListener {
            Log.i("asd","asd");
            val builder = AlertDialog.Builder(this@ProfileActivity)
            builder.setTitle("Выход")
            builder.setMessage("Вы действительно хотите выйти из аккаунта?")
            builder.setPositiveButton("Да") { dialog, which ->
                val fileName = "profile_file"
                val directory = filesDir
                val file = File(directory, fileName)
                try {
                    FileOutputStream(file).close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                setProfile()
            }
            builder.setNegativeButton("Нет") { dialog, which -> dialog.dismiss() }
            val alertDialog = builder.create()
            alertDialog.show()
        }

        val themeSwitcher = findViewById<SwitchMaterial>(R.id.themeSwitcher)
        themeSwitcher.setOnCheckedChangeListener { switcher, checked->

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (toggle!!.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    @SuppressLint("SetTextI18n")
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null) {
            return
        }
        profile = data.getStringExtra("profile")
        val fileName = "profile_file"
        val directory = filesDir
        val file = File(directory, fileName)
        try {
            if (file.exists()) {
                FileOutputStream(file).close()
                val fos = openFileOutput(fileName, MODE_PRIVATE)
                fos.write(profile!!.toByteArray())
            } else {
                val fos = openFileOutput(fileName, MODE_PRIVATE)
                fos.write(profile!!.toByteArray())
                fos.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        setProfile()
        val name = findViewById<TextView>(R.id.name_profile)
        val btn: Button? = findViewById(R.id.btn_profile)
        name.text = "Профиль: $profile"
        btn!!.text = "Сменить профиль"
        super.onActivityResult(requestCode, resultCode, data)
    }

    @SuppressLint("SetTextI18n")
    private fun setProfile() {
        val btn: Button? = findViewById(R.id.btn_profile)
        val out: Button? = findViewById(R.id.btn_out)
        val img: ImageView? = findViewById(R.id.img_profile)
        val fileName = "profile_file"
        val directory = filesDir
        val file = File(directory, fileName)
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
        val name = findViewById<TextView>(R.id.name_profile)
        when (profile) {
            "user2" -> {
                img?.setImageResource(R.drawable.admin)
                btn?.text = "Сменить профиль"
                out?.visibility = View.VISIBLE
            }
            "user1" -> {
                img?.setImageResource(R.drawable.user)
                btn?.text = "Сменить профиль"
                out?.visibility = View.VISIBLE
            }
            else -> {
                img?.setImageResource(R.drawable.emptyuser)
                btn?.text = "Войти"
                out?.visibility = View.GONE
                profile = "Войдите, чтобы продолжить"
            }
        }
        name.text = "$profile"
    }
}