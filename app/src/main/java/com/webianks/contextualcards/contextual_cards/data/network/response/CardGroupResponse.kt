package com.webianks.contextualcards.contextual_cards.data.network.response

import com.google.gson.annotations.SerializedName

data class CardGroupResponse(

    @SerializedName("card_groups")
    val cardGroups: ArrayList<CardGroup>,
)