package com.fampay.contextualcards.contextual_cards.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

const val DISMISSED_CARD_PREFERENCE = "DismissedCardPreference"

fun openUrl(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    try {
        context.startActivity(intent)
       } catch (e: ActivityNotFoundException) {
    }
}

fun setCardDismissed(context: Context,key: String){
    val sharedPref = context.getSharedPreferences(DISMISSED_CARD_PREFERENCE, Context.MODE_PRIVATE)
    with (sharedPref.edit()) {
        putBoolean(key, true)
        apply()
    }
}

fun getIfCardDismissed(context: Context,key: String): Boolean{
    val sharedPref = context.getSharedPreferences(DISMISSED_CARD_PREFERENCE, Context.MODE_PRIVATE)
    return sharedPref.getBoolean(key, false)
}
