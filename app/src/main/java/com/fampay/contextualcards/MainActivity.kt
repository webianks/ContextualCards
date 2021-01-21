package com.fampay.contextualcards

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.fampay.contextualcards.data.network.Networking
import com.fampay.contextualcards.util.ViewModelProviderFactory
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainViewModel = ViewModelProvider(this, ViewModelProviderFactory(MainViewModel::class) {
            MainViewModel(
                    CompositeDisposable(), Networking.create(
                    BuildConfig.BASE_URL,
                    application.cacheDir,
                    5 * 1024 * 1024
            )
            )
        }).get(MainViewModel::class.java)

        mainViewModel.getCardGroups()

        mainViewModel.cardGroupResponseData.observe(this) {
            Log.i(TAG, "CardGroupResponse count ${it.cardGroups.size}")

        }
    }
}