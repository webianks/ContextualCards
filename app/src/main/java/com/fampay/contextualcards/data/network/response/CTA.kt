package com.fampay.contextualcards.data.network.response

import com.google.gson.annotations.SerializedName

data class CTA(

    @SerializedName("text")
    val text: String,

    @SerializedName("bg_color")
    val backgroundColor: String,

    @SerializedName("text_color")
    val textColor: String,

    @SerializedName("url_choice")
    val urlChoice: String,

    @SerializedName("url")
    val url: String,
)