package com.fampay.contextualcards.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fampay.contextualcards.data.network.NetworkService
import com.fampay.contextualcards.data.network.response.CardGroupResponse
import com.fampay.contextualcards.util.Failed
import com.fampay.contextualcards.util.Loading
import com.fampay.contextualcards.util.Success
import com.fampay.contextualcards.util.UiState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val compositeDisposable: CompositeDisposable,
    private val networkService: NetworkService
) : ViewModel() {

    val cardGroupUiState = MutableLiveData<UiState>()

    companion object{
        const val TAG = "MainViewModel"
    }
    fun getCardGroups() {
        cardGroupUiState.postValue(Loading)
        compositeDisposable.addAll(
            networkService.doGetCardGroupsCall()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    cardGroupUiState.postValue(Success(it))
                },{
                    cardGroupUiState.postValue(Failed)
                    Log.e(TAG,"Something went wrong: ${it.message}")
                }))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}