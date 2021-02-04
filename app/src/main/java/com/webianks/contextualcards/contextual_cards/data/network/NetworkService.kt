package com.webianks.contextualcards.contextual_cards.data.network

import com.webianks.contextualcards.contextual_cards.data.network.response.CardGroupResponse
import io.reactivex.Single
import retrofit2.http.GET

interface NetworkService {

    @GET(Endpoints.CARD_GROUPS)
    fun doGetCardGroupsCall(): Single<CardGroupResponse>
}