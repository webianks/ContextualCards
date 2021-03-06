package com.webianks.contextualcards.contextual_cards

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.webianks.contextualcards.contextual_cards.data.network.NetworkService
import com.webianks.contextualcards.contextual_cards.util.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ContextualContainerViewModel(
    private val compositeDisposable: CompositeDisposable,
    private val networkService: NetworkService
) : ViewModel() {

    val cardGroupUiState = MutableLiveData<UiState>()

    companion object {
        const val TAG = "CtxCardViewModel"
    }

    fun getCardGroups(refreshing: Boolean = false) {
        if (refreshing)
            cardGroupUiState.postValue(Refreshing)
        else
            cardGroupUiState.postValue(Loading)
        compositeDisposable.addAll(
            networkService.doGetCardGroupsCall()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    cardGroupUiState.postValue(Success(it))
                }, {
                    cardGroupUiState.postValue(Failed)
                    Log.e(TAG, "Something went wrong: ${it.message}")
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}