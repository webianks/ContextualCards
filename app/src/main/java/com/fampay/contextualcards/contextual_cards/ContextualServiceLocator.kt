package com.fampay.contextualcards.contextual_cards

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fampay.contextualcards.BuildConfig
import com.fampay.contextualcards.contextual_cards.data.network.NetworkService
import com.fampay.contextualcards.contextual_cards.data.network.Networking
import com.fampay.contextualcards.contextual_cards.util.ViewModelProviderFactory
import io.reactivex.disposables.CompositeDisposable

object ContextualServiceLocator {

    private var networkingService: NetworkService? = null

    private fun getNetworkService(application: Application): NetworkService {
        return if (networkingService == null) {
            val networkingService = Networking.create(
                BuildConfig.BASE_URL, application.cacheDir,
                5 * 1024 * 1024
            )
            this.networkingService = networkingService
            networkingService
        } else this.networkingService!!

    }

    fun getContextualCardsViewModel(activity: AppCompatActivity): ContextualContainerViewModel {
        return ViewModelProvider(
            activity,
            ViewModelProviderFactory(ContextualContainerViewModel::class) {
                ContextualContainerViewModel(
                    CompositeDisposable(),
                    getNetworkService(application = activity.application)
                )
            }).get(ContextualContainerViewModel::class.java)
    }

    fun getContextualCardsViewModel(fragment: Fragment): ContextualContainerViewModel {
        return ViewModelProvider(
            fragment,
            ViewModelProviderFactory(ContextualContainerViewModel::class) {
                fragment.activity?.application?.let { getNetworkService(application = it) }?.let {
                    ContextualContainerViewModel(
                        CompositeDisposable(),
                        it
                    )
                }
            }).get(ContextualContainerViewModel::class.java)
    }
}