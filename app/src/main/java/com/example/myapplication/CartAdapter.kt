package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader

class CartAdapter(private val items: List<DVD>, private val context: Context) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.name.text = item.name
        holder.about.text = item.info
        holder.imageView.setImageResource(item.img)
        holder.dvd = item
        holder.trash.visibility = View.VISIBLE
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View, context: Context) : RecyclerView.ViewHolder(view) {
        var name: TextView
        var about: TextView
        var imageView: ImageView
        var dvd: DVD? = null
        var profile: String? = null
        var trash: ImageView

        init {
            name = view.findViewById(R.id.item_name)
            about = view.findViewById(R.id.item_about)
            imageView = view.findViewById(R.id.item_image)
            trash = view.findViewById(R.id.ic_trash)
            view.findViewById<View>(R.id.banner).setOnClickListener {
                val intent = Intent(context, ItemActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("DVD", dvd)
                context.startActivity(intent)
            }
            trash.setOnClickListener {
                val fileName = "profile_file"
                val directory = context.filesDir
                var file = File(directory, fileName)
                if (file.exists()) {
                    try {
                        val fis = context.openFileInput(fileName)
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
                file = File(context.filesDir, "$profile.json")
                val dvds: MutableList<DVD>
                var fis: FileInputStream? = null
                fis = try {
                    FileInputStream(file)
                } catch (e: FileNotFoundException) {
                    throw RuntimeException(e)
                }
                val isr = InputStreamReader(fis)
                val listType = object : TypeToken<ArrayList<DVD?>?>() {}.type
                dvds = Gson().fromJson(isr, listType)
                for (i in dvds.indices) {
                    if (dvds[i].name == dvd?.name) {
                        dvds.removeAt(i)
                    }
                }
                val json = gson.toJson(dvds)
                try {
                    val fos = FileOutputStream(file)
                    fos.write(json.toByteArray())
                    fos.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                context.startActivity(Intent(context, CartActivity::class.java))
            }
        }
    }
}
