package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader

@Suppress("DEPRECATION")
class ItemActivity : AppCompatActivity() {
    private var profile: String? = null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        val dvd = intent.getSerializableExtra("DVD") as DVD?
        val name = findViewById<TextView>(R.id.item_name)
        val about = findViewById<TextView>(R.id.item_about)
        val imageView = findViewById<ImageView>(R.id.item_img)
        val btn = findViewById<Button>(R.id.btn_to_cart)
        if (dvd != null) {
            btn.text = "Добавить в корзину                                   " + dvd.price + "Р"
        }
        btn.setOnClickListener {
            try {
                addToCart(dvd)
            } catch (e: FileNotFoundException) {
                throw RuntimeException(e)
            }
        }
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            if (dvd != null) {
                actionBar.title = dvd.name
            }
        }
        if (dvd != null) {
            name.text = dvd.name
        }
        if (dvd != null) {
            about.text = dvd.info
        }
        if (dvd != null) {
            imageView.setImageResource(dvd.img)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    @Throws(FileNotFoundException::class)
    fun addToCart(dvd: DVD?) {
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
        val gson = Gson()
        file = File(filesDir, "$profile.json")
        val dvds: MutableList<DVD?>
        if (file.length() == 0L || !file.exists()) {
            dvds = ArrayList()
            dvds.add(dvd)
        } else {
            val fis = FileInputStream(file)
            val isr = InputStreamReader(fis)
            val listType = object : TypeToken<ArrayList<DVD?>?>() {}.type
            dvds = Gson().fromJson(isr, listType)
            dvds.add(dvd)
        }
        val json = gson.toJson(dvds)
        try {
            val fos = FileOutputStream(file)
            fos.write(json.toByteArray())
            fos.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}