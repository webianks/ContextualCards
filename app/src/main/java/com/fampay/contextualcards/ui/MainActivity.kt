package com.fampay.contextualcards.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fampay.contextualcards.BuildConfig
import com.fampay.contextualcards.R
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

        mainViewModel = ViewModelProvider(this, ViewModelProviderFactory(MainViewModel::class) {
            MainViewModel(
                CompositeDisposable(), Networking.create(
                    BuildConfig.BASE_URL,
                    application.cacheDir,
                    5 * 1024 * 1024
                )
            )
        }).get(MainViewModel::class.java)

        mainViewModel.getCardGroups()

        addObservers()
    }


    private fun addObservers() {
        mainViewModel.cardGroupUiState.observe(this) {
            when (it) {
                Loading -> {

                }
                is Success<*> -> {
                    val cardGroupResponse = it.data as CardGroupResponse

                    Log.i(TAG, "CardGroupResponse count ${cardGroupResponse.cardGroups.size}")
                    rv_main.layoutManager = LinearLayoutManager(this)
                    rv_main.adapter =
                        MainRecyclerViewAdapter(this, cardGroupResponse.cardGroups) { url ->
                            openUrl(this, url)
                        }
                }
                Failed -> {

                }
                else -> {

                }
            }
        }
    }
}