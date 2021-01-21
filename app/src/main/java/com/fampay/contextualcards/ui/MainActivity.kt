package com.fampay.contextualcards.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fampay.contextualcards.BuildConfig
import com.fampay.contextualcards.ContextualApplication
import com.fampay.contextualcards.R
import com.fampay.contextualcards.ServiceLocator
import com.fampay.contextualcards.data.network.Networking
import com.fampay.contextualcards.data.network.response.CardGroupResponse
import com.fampay.contextualcards.util.*
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ServiceLocator.getMainViewModel(this)
        mainViewModel.getCardGroups()

        addObservers()
    }


    private fun addObservers() {
        mainViewModel.cardGroupUiState.observe(this) {
            when (it) {
                Loading -> {
                    main_progress.visibility = View.VISIBLE
                }
                is Success<*> -> {
                    main_progress.visibility = View.GONE

                    val cardGroupResponse = it.data as CardGroupResponse

                    Log.i(TAG, "CardGroupResponse count ${cardGroupResponse.cardGroups.size}")
                    rv_main.layoutManager = LinearLayoutManager(this)
                    rv_main.adapter =
                        MainRecyclerViewAdapter(this, cardGroupResponse.cardGroups) { url ->
                            openUrl(this, url)
                        }
                }
                Failed -> {
                    main_progress.visibility = View.GONE
                }
                else -> {
                    main_progress.visibility = View.GONE
                }
            }
        }
    }
}