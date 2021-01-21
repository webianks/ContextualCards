package com.fampay.contextualcards.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fampay.contextualcards.R
import com.fampay.contextualcards.ServiceLocator
import com.fampay.contextualcards.data.network.response.CardGroupResponse
import com.fampay.contextualcards.util.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contextual_cards.onCreate(this)

        /*
        swipe_refresh.setOnRefreshListener {
            mainViewModel.getCardGroups(refreshing = true)
        }
         */

    }
}