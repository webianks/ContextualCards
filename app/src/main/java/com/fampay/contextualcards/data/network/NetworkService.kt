package com.fampay.contextualcards.data.network

import com.fampay.contextualcards.data.network.response.CardGroupResponse
import io.reactivex.Single
import retrofit2.http.GET

interface NetworkService {

    @GET(Endpoints.CARD_GROUPS)
    fun doGetCardGroupsCall(): Single<CardGroupResponse>
}