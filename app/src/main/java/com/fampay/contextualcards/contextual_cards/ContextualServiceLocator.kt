package com.fampay.contextualcards.contextual_cards

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fampay.contextualcards.ContextualApplication
import com.fampay.contextualcards.contextual_cards.util.ViewModelProviderFactory
import io.reactivex.disposables.CompositeDisposable

object ContextualServiceLocator {

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