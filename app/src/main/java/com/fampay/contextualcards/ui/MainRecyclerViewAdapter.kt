package com.fampay.contextualcards.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fampay.contextualcards.R

class MainRecyclerViewAdapter(private val context: Context, val list: List<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private inner class DummyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            val string = list[position]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DummyViewHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.item_dummy,
                        parent,
                        false
                    ))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as DummyViewHolder).bind(position)
    }

}