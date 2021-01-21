package com.fampay.contextualcards.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fampay.contextualcards.R
import com.fampay.contextualcards.data.network.response.CardGroup
import kotlinx.android.synthetic.main.item_view_image.view.*
import kotlinx.android.synthetic.main.item_view_small_display.view.*

class MainRecyclerViewAdapter(private val context: Context, val list: List<CardGroup>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object {
        const val HC1 = 1
        const val HC3 = 2
        const val HC5 = 3
        const val HC6 = 4
        const val HC9 = 5
        val viewTypeMap = hashMapOf(
            "HC1" to HC1,
            "HC3" to HC3,
            "HC5" to HC5,
            "HC6" to HC6,
            "HC9" to HC9
        )
    }

    private inner class DummyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            val string = list[position]
        }
    }

    private inner class HC5ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            val cardGroup = list[position]
            val card = cardGroup.cards.first()
            Glide.with(itemView.context).load(card.backgroundImage.imgUrl).into(itemView.iv_image)
        }
    }

    private inner class HC6ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            val cardGroup = list[position]
            val card = cardGroup.cards.first()
            itemView.tv_title.text = card.formattedTitle.text

            if (card.formattedDescription != null) {
                itemView.tv_description.text = card.formattedDescription.text
                itemView.tv_description.visibility = View.VISIBLE
            } else {
                itemView.tv_description.visibility = View.GONE
            }
            Glide.with(itemView.context).load(card.icon.imgUrl).into(itemView.iv_icon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            HC1 -> {
                return DummyViewHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.item_dummy,
                        parent,
                        false
                    )
                )
            }
            HC3 -> {
                return DummyViewHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.item_dummy,
                        parent,
                        false
                    )
                )
            }
            HC5 -> {
                return HC5ViewHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.item_view_image,
                        parent,
                        false
                    )
                )
            }
            HC6 -> {
                return HC6ViewHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.item_view_small_display_arrow,
                        parent,
                        false
                    )
                )
            }
            HC9 -> {
                return DummyViewHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.item_dummy,
                        parent,
                        false
                    )
                )
            }
            else -> {
                return DummyViewHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.item_dummy,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (viewTypeMap[list[position].designType]) {
            HC1 -> {
                (holder as DummyViewHolder).bind(position)
            }
            HC3 -> {
                (holder as DummyViewHolder).bind(position)
            }
            HC5 -> {
                (holder as HC5ViewHolder).bind(position)
            }
            HC6 -> {
                (holder as HC6ViewHolder).bind(position)
            }
            HC9 -> {
                (holder as DummyViewHolder).bind(position)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return viewTypeMap[list[position].designType] ?: 1
    }

}