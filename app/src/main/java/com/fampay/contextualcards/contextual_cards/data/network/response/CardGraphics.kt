package com.fampay.contextualcards.contextual_cards.data.network.response

import com.google.gson.annotations.SerializedName

data class CardGraphics(

    @SerializedName("image_type")
    val imgType: String,

    @SerializedName("image_url")
    val imgUrl: String?,

    @SerializedName("aspect_ratio")
    val aspectRatio: Double,
)