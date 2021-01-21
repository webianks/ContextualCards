package com.fampay.contextualcards.data.network.response

import com.google.gson.annotations.SerializedName

data class FormattedText(

    @SerializedName("text")
    val text: String,

    @SerializedName("align")
    val align: String,

    @SerializedName("entities")
    val entities: List<CardEntity>,
)