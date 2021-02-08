package com.webianks.contextualcards.contextual_cards.list.viewholders

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.webianks.contextualcards.contextual_cards.list.ContextualHorizontalRvAdapter
import com.webianks.contextualcards.contextual_cards.list.ContextualRecyclerViewAdapter.Companion.HC9
import com.webianks.contextualcards.contextual_cards.data.network.response.CardGroup
import kotlinx.android.synthetic.main.item_scrollable.view.*

class HC9ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(cardGroup: CardGroup, actionListener: ((String) -> Unit)?) {

            itemView.recyclerView.adapter = ContextualHorizontalRvAdapter(
                itemView.context,
                cardGroup.cards,
                HC9,
                actionListener,
                cardGroup.height
            )
            itemView.recyclerView.layoutManager = LinearLayoutManager(
                itemView.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            itemView.recyclerView.setHasFixedSize(true)
        }
    }
