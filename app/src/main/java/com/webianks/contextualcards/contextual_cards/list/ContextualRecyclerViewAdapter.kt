package com.webianks.contextualcards.contextual_cards.list


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.webianks.contextualcards.R
import com.webianks.contextualcards.contextual_cards.data.network.response.CardGroup
import com.webianks.contextualcards.contextual_cards.list.viewholders.*

class ContextualRecyclerViewAdapter(
    private val context: Context,
    private val list: ArrayList<CardGroup>,
    private val actionListener: ((String) -> Unit)? = null,
    private val cardDismissListener: (String) -> Unit
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
                (holder as HC1ViewHolder).bind(list[position],actionListener)
            }
            HC3 -> {
                (holder as HC3ViewHolder).bind(list[position],actionListener,cardDismissListener)
            }
            HC5 -> {
                (holder as HC5ViewHolder).bind(list[position],actionListener)
            }
            HC6 -> {
                (holder as HC6ViewHolder).bind(list[position],actionListener)
            }
            HC9 -> {
                (holder as HC9ViewHolder).bind(list[position],actionListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return viewTypeMap[list[position].designType] ?: 1
    }
}