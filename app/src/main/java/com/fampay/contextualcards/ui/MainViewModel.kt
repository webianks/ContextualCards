package com.fampay.contextualcards.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fampay.contextualcards.data.network.NetworkService
import com.fampay.contextualcards.data.network.response.CardGroupResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val compositeDisposable: CompositeDisposable,
    private val networkService: NetworkService
) : ViewModel() {

    val cardGroupResponseData = MutableLiveData<CardGroupResponse>()

    companion object{
        const val TAG = "MainViewModel"
    }
    fun getCardGroups() {
        compositeDisposable.addAll(
            networkService.doGetCardGroupsCall()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    cardGroupResponseData.postValue(it)
                },{
                    Log.e(TAG,"Something went wrong: ${it.message}")
                }))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}