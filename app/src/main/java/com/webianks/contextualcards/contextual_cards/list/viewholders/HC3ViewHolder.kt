package com.webianks.contextualcards.contextual_cards.list.viewholders

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.webianks.contextualcards.R
import com.webianks.contextualcards.contextual_cards.list.ContextualHorizontalRvAdapter
import com.webianks.contextualcards.contextual_cards.list.ContextualRecyclerViewAdapter.Companion.HC3
import com.webianks.contextualcards.contextual_cards.data.network.response.CardGroup
import com.webianks.contextualcards.contextual_cards.util.getIfCardDismissed
import com.webianks.contextualcards.contextual_cards.util.px
import com.webianks.contextualcards.contextual_cards.util.slideAndRevealView
import kotlinx.android.synthetic.main.item_scrollable.view.*
import kotlinx.android.synthetic.main.item_view_big_display.view.*
import kotlinx.android.synthetic.main.item_view_image.view.iv_image

class HC3ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            cardGroup: CardGroup,
            actionListener: ((String) -> Unit)?,
            cardDismissListener: (String) -> Unit
        ) {

            if (cardGroup.isScrollable) {
                itemView.recyclerView.adapter = ContextualHorizontalRvAdapter(
                    itemView.context,
                    cardGroup.cards,
                    HC3,
                    actionListener,
                    cardDismissListener = cardDismissListener
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
                //Add all the big cards with equal widths

                itemView.recyclerView.visibility = View.GONE
                itemView.ll_card_container.visibility = View.VISIBLE
                val weightSum = cardGroup.cards.size
                itemView.ll_card_container.weightSum = weightSum.toFloat()
                itemView.ll_card_container.removeAllViews()

                for (card in cardGroup.cards) {

                    //Don't add this card in the layout if it was dismissed before
                    if(getIfCardDismissed(itemView.context,card.name) || card.isRemindLater)
                        return

                    val bigDisplayCard = LayoutInflater.from(itemView.context).inflate(
                        R.layout.item_view_big_display,
                        itemView.ll_card_container, false
                    )

                    val layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    layoutParams.setMargins(8.px, 8.px, 8.px, 8.px)
                    layoutParams.weight = 1.0f
                    bigDisplayCard.layoutParams = layoutParams

                    itemView.ll_card_container.addView(bigDisplayCard)

                    card.backgroundImage.imgUrl?.let {
                        Glide.with(itemView.context).load(it)
                            .into(itemView.iv_image)
                    }

                    bigDisplayCard.tv_big_title.text = card.formattedTitle.text
                    if (card.formattedDescription != null) {
                        bigDisplayCard.tv_big_description.text = card.formattedDescription.text
                        bigDisplayCard.tv_big_description.visibility = View.VISIBLE
                    } else
                        bigDisplayCard.tv_big_description.visibility = View.GONE

                    if (card.ctaList.isNotEmpty()) {
                        val cta = card.ctaList.first()
                        bigDisplayCard.bt_cta.text = cta.text

                        if (cta.textColor != null)
                            bigDisplayCard.bt_cta.setTextColor(Color.parseColor(cta.textColor))

                        if (cta.backgroundColor != null)
                            bigDisplayCard.bt_cta.setBackgroundColor(Color.parseColor(cta.backgroundColor))

                        bigDisplayCard.bt_cta.visibility = View.VISIBLE
                    } else
                        bigDisplayCard.bt_cta.visibility = View.GONE

                    //if (bigDisplayCard is MaterialCardView && card.bgColor != null)
                    //bigDisplayCard.setCardBackgroundColor(Color.parseColor(card.bgColor))

                    bigDisplayCard.bt_cta.setOnClickListener {
                        cardGroup.cards.first().ctaList.first().url?.let { url ->
                            actionListener?.invoke(url)
                        }
                    }
                    bigDisplayCard.setOnLongClickListener {
                        slideAndRevealView(
                            itemView.view_foreground,
                            itemView.view_background,
                            card
                        )
                        true
                    }

                    bigDisplayCard.cv_remind_later.setOnClickListener {
                        //list.removeAt(adapterPosition)
                        //notifyItemRemoved(adapterPosition)
                        itemView.ll_card_container.removeView(bigDisplayCard)
                        card.isRemindLater = true
                    }

                    /**
                     *  Here using name of the card as unique key because we may have other cards
                     *  in the group and we dont have id in the schema of card to use it as unique
                     *  for the same reason we can't use id of the card group also.
                     */
                    bigDisplayCard.cv_dismiss_now.setOnClickListener {
                        cardDismissListener(card.name)
                        itemView.ll_card_container.removeView(bigDisplayCard)
                    }
                }
            }
        }
    }
