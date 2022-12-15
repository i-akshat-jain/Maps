package com.one0one.photoapp

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.util.Log.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.one0one.photoapp.model.UserMap
import org.w3c.dom.Text

private const val TAG = "MapsAdapter"
class MapsAdapter(val context: Context, val userMaps: List<UserMap>, val onClickListener: OnClickListener): RecyclerView.Adapter<MapsAdapter.ViewHolder>() {


    // to notify the main activity on click

    // by defining interface in the map adapter and passing the interface in the mapadpater
    interface OnClickListener{
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //inflate the view and grab the inside of the viewHolder
        val view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userMap = userMaps[position]
        holder.itemView.setOnClickListener(View.OnClickListener { view ->
            Log.i(TAG, "Tapped Position $position")
            onClickListener.onItemClick(position)

        })
        val textViewTitle = holder.itemView.findViewById<TextView>(android.R.id.text1)
        textViewTitle.text = userMap.title

    }

    override fun getItemCount() = userMaps.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}

