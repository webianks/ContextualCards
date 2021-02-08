package com.webianks.contextualcards.contextual_cards.list.viewholders

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.webianks.contextualcards.R
import com.webianks.contextualcards.contextual_cards.list.ContextualHorizontalRvAdapter
import com.webianks.contextualcards.contextual_cards.list.ContextualRecyclerViewAdapter.Companion.HC1
import com.webianks.contextualcards.contextual_cards.data.network.response.CardGroup
import com.webianks.contextualcards.contextual_cards.util.px
import kotlinx.android.synthetic.main.item_scrollable.view.*
import kotlinx.android.synthetic.main.item_view_small_display.view.*

 class HC1ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(cardGroup: CardGroup, actionListener: ((String) -> Unit)?) {

            if (cardGroup.isScrollable) {

                itemView.recyclerView.adapter = ContextualHorizontalRvAdapter(
                    itemView.context,
                    cardGroup.cards,
                    HC1,
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

                itemView.recyclerView.visibility = View.GONE
                itemView.ll_card_container.visibility = View.VISIBLE

                val weightSum = cardGroup.cards.size
                itemView.ll_card_container.weightSum = weightSum.toFloat()
                itemView.ll_card_container.removeAllViews()

                for (card in cardGroup.cards) {

                    val smallDisplayCard = LayoutInflater.from(itemView.context).inflate(
                        R.layout.item_view_small_display,
                        itemView.ll_card_container, false
                    )

                    val layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    layoutParams.setMargins(8.px, 4.px, 4.px, 8.px)
                    layoutParams.weight = 1.0f
                    smallDisplayCard.layoutParams = layoutParams

                    itemView.ll_card_container.addView(smallDisplayCard)

                    smallDisplayCard.tv_title.text = card.formattedTitle.text
                    if (card.formattedDescription != null) {
                        smallDisplayCard.tv_description.text = card.formattedDescription.text
                        smallDisplayCard.tv_description.visibility = View.VISIBLE
                    } else
                        smallDisplayCard.tv_description.visibility = View.GONE


                    card.icon.imgUrl?.let {
                        Glide.with(itemView.context).load(it)
                            .placeholder(R.drawable.default_icon_background)
                            .error(R.drawable.default_icon_background)
                            .into(smallDisplayCard.iv_icon)
                    }

                    if (smallDisplayCard is MaterialCardView && card.bgColor != null)
                        smallDisplayCard.setCardBackgroundColor(Color.parseColor(card.bgColor))

                    smallDisplayCard.setOnClickListener {
                        actionListener?.invoke(card.url)
                    }
                }
            }
        }
    }
