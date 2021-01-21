package com.fampay.contextualcards.contextual_cards.data.network.response

import com.google.gson.annotations.SerializedName

data class Card(

    @SerializedName("name")
    val name: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("formatted_title")
    val formattedTitle: FormattedText,

    @SerializedName("description")
    val description: String,

    @SerializedName("formatted_description")
    val formattedDescription: FormattedText?,

    @SerializedName("icon")
    val icon: CardGraphics,

    @SerializedName("bg_image")
    val backgroundImage: CardGraphics,

    @SerializedName("url")
    val url: String,

    @SerializedName("bg_color")
    val bgColor: String?,

    @SerializedName("cta")
    val ctaList: List<CTA>,

    @SerializedName("is_disabled")
    val isDisabled: Boolean,

    var isOpen: Boolean = false
)