package com.webianks.contextualcards.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.webianks.contextualcards.R
import kotlinx.android.synthetic.main.activity_fragment_test.*

/**
 * Activity used for testing only
 * */
class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_test)

        contextual_cards.init(this)
    }
}