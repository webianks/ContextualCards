package com.fampay.contextualcards.demo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fampay.contextualcards.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Activity used for testing only
 * */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contextual_cards.init(this)

        //Uncomment to test starting another activity with contextual card inside it
        //startActivity(Intent(this,SecondActivity::class.java))
    }
}