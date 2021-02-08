package com.webianks.contextualcards.contextual_cards.list.viewholders

import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.webianks.contextualcards.R
import com.webianks.contextualcards.contextual_cards.list.ContextualHorizontalRvAdapter
import com.webianks.contextualcards.contextual_cards.list.ContextualRecyclerViewAdapter.Companion.HC5
import com.webianks.contextualcards.contextual_cards.data.network.response.CardGroup
import com.webianks.contextualcards.contextual_cards.util.px
import kotlinx.android.synthetic.main.item_scrollable.view.*
import kotlinx.android.synthetic.main.item_view_big_display.view.*

class HC5ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(cardGroup: CardGroup, actionListener: ((String) -> Unit)?) {

            if (cardGroup.isScrollable) {
                itemView.recyclerView.adapter = ContextualHorizontalRvAdapter(
                    itemView.context,
                    cardGroup.cards,
                    HC5,
                    actionListener
                )
                itemView.recyclerView.layoutManager = LinearLayoutManager(
                    itemView.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                itemView.recyclerView.setHasFixedSize(true)
                itemView.recyclerView.visibility = View.VISIBLE
                itemView.ll_card_container.visibility = View.GONE

            } else {

                //Add all the image cards with equal widths

                itemView.recyclerView.visibility = View.GONE
                itemView.ll_card_container.visibility = View.VISIBLE
                val weightSum = cardGroup.cards.size
                itemView.ll_card_container.weightSum = weightSum.toFloat()
                itemView.ll_card_container.removeAllViews()

                for (card in cardGroup.cards) {

                    val imageCard = LayoutInflater.from(itemView.context).inflate(
                        R.layout.item_view_image,
                        itemView.ll_card_container, false
                    )

                    val layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    layoutParams.setMargins(8.px, 8.px, 8.px, 8.px)
                    layoutParams.weight = 1.0f
                    imageCard.layoutParams = layoutParams

                    itemView.ll_card_container.addView(imageCard)

                    card.backgroundImage.imgUrl?.let {
                        Glide.with(imageCard.context).load(it)
                            .into(imageCard.iv_image)
                    }

                    imageCard.setOnClickListener {
                        actionListener?.invoke(card.url)
                    }
                }
            }
        }
    }
