package com.fampay.contextualcards.contextual_cards

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fampay.contextualcards.R
import com.fampay.contextualcards.contextual_cards.data.network.response.Card
import com.fampay.contextualcards.contextual_cards.data.network.response.CardGroup
import com.fampay.contextualcards.contextual_cards.util.getIfCardDismissed
import com.fampay.contextualcards.contextual_cards.util.px
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.item_scrollable.view.*
import kotlinx.android.synthetic.main.item_view_big_display.view.*
import kotlinx.android.synthetic.main.item_view_image.view.iv_image
import kotlinx.android.synthetic.main.item_view_small_display.view.*

class ContextualRvAdapter(
    private val context: Context, val list: ArrayList<CardGroup>,
    val actionListener: ((String) -> Unit)? = null,
    val cardDismissListener: (String) -> Unit
) :
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

                itemView.recyclerView.adapter = ContextualHorizontalRvAdapter(
                    context,
                    cardGroup.cards,
                    HC1,
                    actionListener
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

    private inner class HC3ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {

            val cardGroup = list[position]

            if (cardGroup.isScrollable) {
                itemView.recyclerView.adapter = ContextualHorizontalRvAdapter(
                    context,
                    cardGroup.cards,
                    HC3,
                    actionListener,
                    cardDismissListener = cardDismissListener
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
                //Add all the big cards with equal widths

                itemView.recyclerView.visibility = View.GONE
                itemView.ll_card_container.visibility = View.VISIBLE
                val weightSum = cardGroup.cards.size
                itemView.ll_card_container.weightSum = weightSum.toFloat()
                itemView.ll_card_container.removeAllViews()

                for (card in cardGroup.cards) {

                    //Don't add this card in the layout if it was dismissed before
                    if(getIfCardDismissed(context,card.name) || card.isRemindLater)
                        return

                    val bigDisplayCard = LayoutInflater.from(context).inflate(
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
                        list[adapterPosition].cards.first().ctaList.first().url?.let { url ->
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

    private inner class HC5ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            val cardGroup = list[position]

            if (cardGroup.isScrollable) {
                itemView.recyclerView.adapter = ContextualHorizontalRvAdapter(
                    context,
                    cardGroup.cards,
                    HC5,
                    actionListener
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

                //Add all the image cards with equal widths

                itemView.recyclerView.visibility = View.GONE
                itemView.ll_card_container.visibility = View.VISIBLE
                val weightSum = cardGroup.cards.size
                itemView.ll_card_container.weightSum = weightSum.toFloat()
                itemView.ll_card_container.removeAllViews()

                for (card in cardGroup.cards) {

                    val imageCard = LayoutInflater.from(context).inflate(
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

    private inner class HC6ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            val cardGroup = list[position]

            if (cardGroup.isScrollable) {
                itemView.recyclerView.adapter = ContextualHorizontalRvAdapter(
                    context,
                    cardGroup.cards,
                    HC5,
                    actionListener
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
                //Add all the arrow cards with equal widths

                itemView.recyclerView.visibility = View.GONE
                itemView.ll_card_container.visibility = View.VISIBLE
                val weightSum = cardGroup.cards.size
                itemView.ll_card_container.weightSum = weightSum.toFloat()
                itemView.ll_card_container.removeAllViews()

                for (card in cardGroup.cards) {

                    val arrowCard = LayoutInflater.from(context).inflate(
                        R.layout.item_view_small_display_arrow,
                        itemView.ll_card_container, false
                    )

                    val layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    layoutParams.setMargins(8.px, 8.px, 8.px, 8.px)
                    layoutParams.weight = 1.0f
                    arrowCard.layoutParams = layoutParams

                    itemView.ll_card_container.addView(arrowCard)

                    arrowCard.tv_title.text = card.formattedTitle.text

                    if (card.formattedDescription != null) {
                        arrowCard.tv_description.text = card.formattedDescription.text
                        arrowCard.tv_description.visibility = View.VISIBLE
                    } else {
                        arrowCard.tv_description.visibility = View.GONE
                    }

                    card.icon.imgUrl?.let {
                        Glide.with(arrowCard.context)
                            .load(it)
                            .into(arrowCard.iv_icon)
                    }

                    arrowCard.setOnClickListener {
                        actionListener?.invoke(card.url)
                    }
                }
            }
        }
    }

    private inner class HC9ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            val cardGroup = list[position]
            itemView.recyclerView.adapter = ContextualHorizontalRvAdapter(
                context,
                cardGroup.cards,
                HC9,
                actionListener,
                cardGroup.height
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
                        R.layout.item_scrollable,
                        parent,
                        false
                    )
                )
            }
            HC5 -> {
                return HC5ViewHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.item_scrollable,
                        parent,
                        false
                    )
                )
            }
            HC6 -> {
                return HC6ViewHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.item_scrollable,
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


    /**
     * Animate the foreground view by revealing the background view with actions
     * and also vibrate at the same time
     */
    fun slideAndRevealView(foreground: View, background: View, card: Card) {
        if (card.isOpen) {
            val animation = ObjectAnimator.ofFloat(foreground, "translationX", 0f)
            animation.duration = 100
            background.visibility = View.GONE
            animation.start()
            val v = foreground.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
            v!!.vibrate(50)
            card.isOpen = false
        } else {
            background.visibility = View.VISIBLE

            //TODO remove this hadcoded value
            val animation = ObjectAnimator.ofFloat(foreground, "translationX", 180.px.toFloat())
            animation.duration = 100
            animation.start()
            val v = foreground.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
            v!!.vibrate(50)
            card.isOpen = true
        }
    }

}