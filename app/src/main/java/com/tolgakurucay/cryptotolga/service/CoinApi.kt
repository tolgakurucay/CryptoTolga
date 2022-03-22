package com.tolgakurucay.cryptotolga.service

import com.tolgakurucay.cryptotolga.model.Coin
import io.reactivex.Single
import retrofit2.http.GET

interface CoinApi {

    //BASE URL
    //https://api.nomics.com/v1/

    //EXTENSION URL
    //
    //2f10fb4972dc05c2ccc090f70b02bf49719edfba

    @GET("api/v3/coins/list")
    fun getAll() : Single<List<Coin>>


}