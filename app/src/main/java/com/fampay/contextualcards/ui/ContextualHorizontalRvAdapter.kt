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
import com.fampay.contextualcards.ui.ContextualRvAdapter.Companion.HC1
import com.fampay.contextualcards.ui.ContextualRvAdapter.Companion.HC5
import com.fampay.contextualcards.ui.ContextualRvAdapter.Companion.HC9
import com.fampay.contextualcards.util.px
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.item_dynamic_width.view.*
import kotlinx.android.synthetic.main.item_view_scrollable_image.view.*
import kotlinx.android.synthetic.main.item_view_small_display.view.*

class ContextualHorizontalRvAdapter(
    private val context: Context,
    val list: List<Card>,
    val type: Int,
    val actionListener: ((String) -> Unit)? = null,
    val hc9Height: Int? = null,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mRecyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }


    private inner class HC1ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                actionListener?.invoke(list[adapterPosition].url)
            }
        }

        fun bind(position: Int) {
            val card = list[position]
            itemView.tv_title.text = card.formattedTitle.text
            if (card.formattedDescription != null) {
                itemView.tv_description.text = card.formattedDescription.text
                itemView.tv_description.visibility = View.VISIBLE
            } else
                itemView.tv_description.visibility = View.GONE

            card.icon.imgUrl?.let {
                Glide.with(itemView.context)
                    .load(it)
                    .placeholder(R.drawable.default_icon_background)
                    .error(R.drawable.default_icon_background)
                    .into(itemView.iv_icon)
            }

            if (itemView is MaterialCardView && card.bgColor != null)
                itemView.setCardBackgroundColor(Color.parseColor(card.bgColor))
        }
    }

    private inner class HC5ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                actionListener?.invoke(list[adapterPosition].url)
            }
        }

        fun bind(position: Int) {
            val card = list[position]

            val layoutParams = itemView.layoutParams
            layoutParams.width = (layoutParams.height * card.backgroundImage.aspectRatio).toInt()

            card.backgroundImage.imgUrl?.let {
                Glide.with(itemView.context).load(it)
                    .into(itemView.iv_scrollable_image)
            }
        }
    }

    private inner class HC9ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                actionListener?.invoke(list[adapterPosition].url)
            }
        }

        fun bind(position: Int) {
            val card = list[position]
            val layoutParams = itemView.layoutParams
            //set the hc9 card height sent from the parent card_group
            hc9Height?.let {
                layoutParams.height = it.px
            }
            //set width of this card based on the aspect ratio od the image
            layoutParams.width = (layoutParams.height * card.backgroundImage.aspectRatio).toInt()

            card.backgroundImage.imgUrl?.let {
                Glide.with(itemView.context).load(it)
                    .into(itemView.iv_image_dynamic)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (type) {
            HC1 -> {
                HC1ViewHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.item_view_small_display, parent, false)
                )
            }
            HC5 -> {
                HC5ViewHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.item_view_scrollable_image, parent, false)
                )
            }
            HC9 -> {
                HC9ViewHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.item_dynamic_width, parent, false)
                )
            }
            else -> {
                HC9ViewHolder(
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

        when (type) {
            HC5 -> {
                (holder as HC5ViewHolder).bind(position)
            }
            HC1 -> {
                (holder as HC1ViewHolder).bind(position)
            }
            else -> {
                (holder as HC9ViewHolder).bind(position)
            }
        }
    }
}