package com.fampay.contextualcards.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fampay.contextualcards.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contextual_cards.onCreate(this)
    }
}