package com.webianks.contextualcards.contextual_cards.util

sealed class UiState

object Loading : UiState()
object Refreshing : UiState()
object RefreshingSucceeded : UiState()
object RefreshingFailed : UiState()
data class Success<T>(val data: T) : UiState()
object NoData : UiState()
object Failed : UiState()
