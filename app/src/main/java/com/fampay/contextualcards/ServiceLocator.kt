package com.fampay.contextualcards

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fampay.contextualcards.contextual_cards.ContextualContainerViewModel
import com.fampay.contextualcards.util.ViewModelProviderFactory
import io.reactivex.disposables.CompositeDisposable

object ServiceLocator {

    fun getContextualCardsViewModel(activity: AppCompatActivity): ContextualContainerViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(ContextualContainerViewModel::class) {
            ContextualContainerViewModel(
                CompositeDisposable(),
                (activity.application as ContextualApplication).getNetworkService()
            )
        }).get(ContextualContainerViewModel::class.java)
    }

    fun getContextualCardsViewModel(fragment: Fragment): ContextualContainerViewModel {
        return ViewModelProvider(fragment, ViewModelProviderFactory(ContextualContainerViewModel::class) {
            ContextualContainerViewModel(
                CompositeDisposable(),
                (fragment.activity?.application as ContextualApplication).getNetworkService()
            )
        }).get(ContextualContainerViewModel::class.java)
    }
}