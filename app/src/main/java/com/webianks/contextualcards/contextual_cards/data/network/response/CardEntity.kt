package com.webianks.contextualcards.contextual_cards.data.network.response

import com.google.gson.annotations.SerializedName


data class CardEntity(

    @SerializedName("text")
    val text: String,

    @SerializedName("color")
    val color: String?,

    @SerializedName("url")
    val url: String?,

    @SerializedName("font_style")
    val fontStyle: String?,

)