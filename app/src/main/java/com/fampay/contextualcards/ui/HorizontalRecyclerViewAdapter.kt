package com.fampay.contextualcards.ui

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fampay.contextualcards.R
import com.fampay.contextualcards.data.network.response.Card
import com.fampay.contextualcards.ui.MainRecyclerViewAdapter.Companion.HC1
import com.fampay.contextualcards.ui.MainRecyclerViewAdapter.Companion.HC9
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.item_dynamic_width.view.*
import kotlinx.android.synthetic.main.item_view_small_display.view.*

class HorizontalRecyclerViewAdapter(
    private val context: Context,
    val list: List<Card>,
    val type: Int,
    val actionListener: ((String) -> Unit)? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mRecyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    private inner class HorizontalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                actionListener?.invoke(list[adapterPosition].url)
            }
        }

        fun bind(position: Int) {

            val card = list[position]

            if (type == HC1) {

                itemView.tv_title.text = card.formattedTitle.text
                if (card.formattedDescription != null) {
                    itemView.tv_description.text = card.formattedDescription.text
                    itemView.tv_description.visibility = View.VISIBLE
                } else
                    itemView.tv_description.visibility = View.GONE

                Glide.with(itemView.context)
                    .load(card.icon.imgUrl)
                    .placeholder(R.drawable.default_icon_background)
                    .error(R.drawable.default_icon_background)
                    .into(itemView.iv_icon)

                if (itemView is MaterialCardView && card.bgColor != null)
                    itemView.setCardBackgroundColor(Color.parseColor(card.bgColor))

            } else {
                val layoutParams = itemView.layoutParams
                layoutParams.width =
                    (layoutParams.height / card.backgroundImage.aspectRatio).toInt()
                Glide.with(itemView.context).load(card.backgroundImage.imgUrl)
                    .into(itemView.iv_image_dynamic)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (type) {
            HC1 -> {
                HorizontalViewHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.item_view_small_display, parent, false)
                )
            }
            HC9 -> {
                HorizontalViewHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.item_dynamic_width, parent, false)
                )
            }
            else -> {
                HorizontalViewHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.item_dynamic_width, parent, false)
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HorizontalViewHolder).bind(position)
    }
}