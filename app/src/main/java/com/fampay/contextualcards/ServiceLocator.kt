package com.fampay.contextualcards

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.fampay.contextualcards.ui.MainViewModel
import com.fampay.contextualcards.util.ViewModelProviderFactory
import io.reactivex.disposables.CompositeDisposable

object ServiceLocator {

    fun getMainViewModel(activity: AppCompatActivity): MainViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(MainViewModel::class) {
            MainViewModel(
                CompositeDisposable(),  (activity.application as ContextualApplication).getNetworkService()
            )
        }).get(MainViewModel::class.java)
    }
}