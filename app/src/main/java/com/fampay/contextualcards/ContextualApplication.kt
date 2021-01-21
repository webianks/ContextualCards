package com.fampay.contextualcards

import android.app.Application
import com.fampay.contextualcards.data.network.NetworkService
import com.fampay.contextualcards.data.network.Networking

class ContextualApplication : Application() {

    private lateinit var networkingService: NetworkService

    override fun onCreate() {
        super.onCreate()

       networkingService =  Networking.create(
            BuildConfig.BASE_URL, cacheDir,
            5 * 1024 * 1024
        )
    }

    fun getNetworkService() = networkingService

}