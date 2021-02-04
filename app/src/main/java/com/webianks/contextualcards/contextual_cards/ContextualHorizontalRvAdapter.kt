package com.webianks.contextualcards.contextual_cards

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.webianks.contextualcards.R
import com.webianks.contextualcards.contextual_cards.data.network.response.Card
import com.webianks.contextualcards.contextual_cards.ContextualRvAdapter.Companion.HC1
import com.webianks.contextualcards.contextual_cards.ContextualRvAdapter.Companion.HC3
import com.webianks.contextualcards.contextual_cards.ContextualRvAdapter.Companion.HC5
import com.webianks.contextualcards.contextual_cards.ContextualRvAdapter.Companion.HC6
import com.webianks.contextualcards.contextual_cards.ContextualRvAdapter.Companion.HC9
import com.webianks.contextualcards.contextual_cards.util.px
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.item_dynamic_width.view.*
import kotlinx.android.synthetic.main.item_view_big_display.view.*
import kotlinx.android.synthetic.main.item_view_big_display.view.iv_image
import kotlinx.android.synthetic.main.item_view_scrollable_image.view.*
import kotlinx.android.synthetic.main.item_view_small_display.view.*

class ContextualHorizontalRvAdapter(
    private val context: Context,
    val list: ArrayList<Card>,
    private val type: Int,
    val actionListener: ((String) -> Unit)? = null,
    val hc9Height: Int? = null,
    val cardDismissListener: ((String) -> Unit)? = null

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

    private inner class HC3ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.bt_cta.setOnClickListener {
                list[adapterPosition].ctaList.first().url?.let { url ->
                    actionListener?.invoke(url)
                }
            }
            itemView.setOnLongClickListener {
                slideAndRevealView(
                    itemView.view_foreground,
                    itemView.view_background,
                    list[adapterPosition]
                )
                true
            }


            itemView.cv_remind_later.setOnClickListener {
                list.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }

            /**
             *  Here using name of the card as unique key because we may have other cards
             *  in the group and we don't have id in the schema of card to use it as unique
             *  for the same reason we can't use id of the card group also.
             */
            itemView.cv_dismiss_now.setOnClickListener {
                cardDismissListener?.invoke(list[adapterPosition].name)
                list.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }

        fun bind(position: Int) {
            val card = list[position]


            card.backgroundImage.imgUrl?.let {
                Glide.with(itemView.context).load(it)
                    .into(itemView.iv_image)
            }

            itemView.tv_big_title.text = card.formattedTitle.text
            if (card.formattedDescription != null) {
                itemView.tv_big_description.text = card.formattedDescription.text
                itemView.tv_big_description.visibility = View.VISIBLE
            } else
                itemView.tv_big_description.visibility = View.GONE

            if (card.ctaList.isNotEmpty()) {
                val cta = card.ctaList.first()
                itemView.bt_cta.text = cta.text

                if (cta.textColor != null)
                    itemView.bt_cta.setTextColor(Color.parseColor(cta.textColor))

                if (cta.backgroundColor != null)
                    itemView.bt_cta.setBackgroundColor(Color.parseColor(cta.backgroundColor))

                itemView.bt_cta.visibility = View.VISIBLE
            } else
                itemView.bt_cta.visibility = View.GONE
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

    private inner class HC6ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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
                    .into(itemView.iv_icon)
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
            HC3 -> {
                HC5ViewHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.item_view_big_display, parent, false)
                )
            }
            HC5 -> {
                HC5ViewHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.item_view_scrollable_image, parent, false)
                )
            }
            HC6 -> {
                HC6ViewHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.item_view_small_display_arrow, parent, false)
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
            HC6 -> {
                (holder as HC6ViewHolder).bind(position)
            }
            HC3 -> {
                (holder as HC3ViewHolder).bind(position)
            }
            HC1 -> {
                (holder as HC1ViewHolder).bind(position)
            }
            else -> {
                (holder as HC9ViewHolder).bind(position)
            }
        }
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
            //TODO remove this hardcoded value
            val animation = ObjectAnimator.ofFloat(foreground, "translationX", 180.px.toFloat())
            animation.duration = 100

            animation.start()
            val v = foreground.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
            v!!.vibrate(50)
            card.isOpen = true
        }
    }
}