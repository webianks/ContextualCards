package com.webianks.contextualcards.contextual_cards.util

import android.animation.ObjectAnimator
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Vibrator
import android.view.View
import com.webianks.contextualcards.contextual_cards.data.network.response.Card

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

/**
 * Animate the foreground view by revealing the background view with actions
 * and also vibrate at the same time
 */
fun slideAndRevealView(foreground: View, background: View, card: Card) {
    if (card.isOpen) {
        val animation = ObjectAnimator.ofFloat(foreground, "translationX", 0f)
        animation.duration = 100
        background.visibility = View.GONE
        animation.start()
        val v = foreground.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
        v!!.vibrate(50)
        card.isOpen = false
    } else {
        background.visibility = View.VISIBLE

        //TODO remove this hadcoded value
        val animation = ObjectAnimator.ofFloat(foreground, "translationX", 180.px.toFloat())
        animation.duration = 100
        animation.start()
        val v = foreground.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
        v!!.vibrate(50)
        card.isOpen = true
    }
}


/*
fun formatText(content: String,entities: List<CardEntity>) : String{

    val stringContent = "Hello {}, you look lonely! Why don't you do {}!"
    val contents = stringContent.split("{}")
    entities.forEach{
        stringContent.replaceFirst("{}",it.text)
    }
}
*/
