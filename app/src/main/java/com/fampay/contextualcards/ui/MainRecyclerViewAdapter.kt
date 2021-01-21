package com.fampay.contextualcards.ui

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fampay.contextualcards.R
import com.fampay.contextualcards.data.network.response.CardGroup
import com.fampay.contextualcards.util.px
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.item_scrollable.view.*
import kotlinx.android.synthetic.main.item_view_big_display.view.*
import kotlinx.android.synthetic.main.item_view_image.view.*
import kotlinx.android.synthetic.main.item_view_image.view.iv_image
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

    private inner class HC1ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            val cardGroup = list[position]

            if (cardGroup.isScrollable) {

                itemView.recyclerView.adapter = HorizontalRecyclerViewAdapter(
                    context,
                    cardGroup.cards,
                    HC1
                )
                itemView.recyclerView.layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                itemView.recyclerView.setHasFixedSize(true)
                itemView.recyclerView.visibility = View.VISIBLE
                itemView.ll_card_container.visibility = View.GONE

            } else {

                itemView.recyclerView.visibility = View.GONE
                itemView.ll_card_container.visibility = View.VISIBLE

                val weightSum = cardGroup.cards.size
                itemView.ll_card_container.weightSum = weightSum.toFloat()
                itemView.ll_card_container.removeAllViews()

                for (card in cardGroup.cards) {

                    val smallDisplayCard = LayoutInflater.from(context).inflate(
                        R.layout.item_view_small_display,
                        itemView.ll_card_container, false
                    )

                    val layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    layoutParams.setMargins(8.px, 8.px, 8.px, 8.px)
                    layoutParams.weight = 1.0f
                    smallDisplayCard.layoutParams = layoutParams

                    itemView.ll_card_container.addView(smallDisplayCard)

                    smallDisplayCard.tv_title.text = card.formattedTitle.text
                    if (card.formattedDescription != null) {
                        smallDisplayCard.tv_description.text = card.formattedDescription.text
                        smallDisplayCard.tv_description.visibility = View.VISIBLE
                    } else {
                        smallDisplayCard.tv_description.visibility = View.GONE
                    }
                    Glide.with(itemView.context).load(card.icon.imgUrl)
                        .placeholder(R.drawable.default_icon_background)
                        .error(R.drawable.default_icon_background)
                        .into(smallDisplayCard.iv_icon)
                    if (smallDisplayCard is MaterialCardView && card.bgColor != null)
                        smallDisplayCard.setCardBackgroundColor(Color.parseColor(card.bgColor))

                }
            }
        }
    }

    private inner class HC3ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(position: Int) {
            val cardGroup = list[position]
            val card = cardGroup.cards.first()

            Glide.with(itemView.context).load(card.backgroundImage.imgUrl).into(itemView.iv_image)

            itemView.tv_big_title.text = card.formattedTitle.text

            if (card.formattedDescription != null) {
                itemView.tv_big_description.text = card.formattedDescription.text
                itemView.tv_big_description.visibility = View.VISIBLE
            } else
                itemView.tv_big_description.visibility = View.GONE

            if (card.ctaList.isNotEmpty()) {
                val cta = card.ctaList.first()
                itemView.bt_cta.text = cta.text
                itemView.bt_cta.setTextColor(Color.parseColor(cta.textColor))
                itemView.bt_cta.setBackgroundColor(Color.parseColor(cta.backgroundColor))
                itemView.bt_cta.visibility = View.VISIBLE
            } else
                itemView.bt_cta.visibility = View.GONE

            //if (itemView is MaterialCardView && card.bgColor != null)
            //itemView.setCardBackgroundColor(Color.parseColor(card.bgColor))

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

    private inner class HC9ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            val cardGroup = list[position]
            itemView.recyclerView.adapter = HorizontalRecyclerViewAdapter(
                context,
                cardGroup.cards,
                HC9
            )
            itemView.recyclerView.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            itemView.recyclerView.setHasFixedSize(true)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            HC1 -> {
                return HC1ViewHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.item_scrollable,
                        parent,
                        false
                    )
                )
            }
            HC3 -> {
                return HC3ViewHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.item_view_big_display,
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
                return HC9ViewHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.item_scrollable,
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
                (holder as HC1ViewHolder).bind(position)
            }
            HC3 -> {
                (holder as HC3ViewHolder).bind(position)
            }
            HC5 -> {
                (holder as HC5ViewHolder).bind(position)
            }
            HC6 -> {
                (holder as HC6ViewHolder).bind(position)
            }
            HC9 -> {
                (holder as HC9ViewHolder).bind(position)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return viewTypeMap[list[position].designType] ?: 1
    }

}