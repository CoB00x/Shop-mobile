package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val items: List<DVD>?, private val context: Context) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items!![position]
        holder.name.text = item.name
        holder.about.text = item.info
        holder.imageView.setImageResource(item.img)
        holder.dvd = item
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    class ViewHolder(view: View, context: Context) : RecyclerView.ViewHolder(view) {
        var name: TextView
        var about: TextView
        var imageView: ImageView
        var dvd: DVD? = null
        var profile: String? = null

        init {
            name = view.findViewById(R.id.item_name)
            about = view.findViewById(R.id.item_about)
            imageView = view.findViewById(R.id.item_image)
            view.findViewById<View>(R.id.banner).setOnClickListener {
                val intent = Intent(context, ItemActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("DVD", dvd)
                context.startActivity(intent)
            }
        }
    }
}
