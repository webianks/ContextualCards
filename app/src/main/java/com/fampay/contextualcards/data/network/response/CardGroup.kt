package com.fampay.contextualcards.data.network.response

import com.google.gson.annotations.SerializedName

data class CardGroup(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("design_type")
    val designType: String,

    @SerializedName("card_type")
    val cardType: Int,

    @SerializedName("cards")
    val cards: ArrayList<Card>,

    @SerializedName("is_scrollable")
    val isScrollable: Boolean,

    @SerializedName("level")
    val level: Int,

    @SerializedName("height")
    val height: Int,
)