package com.fampay.contextualcards.data.network.response

import com.google.gson.annotations.SerializedName

data class CardGroupResponse(

    @SerializedName("card_groups")
    val cardGroups: List<CardGroup>,
)