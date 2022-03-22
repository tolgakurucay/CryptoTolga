package com.tolgakurucay.cryptotolga.service

import com.tolgakurucay.cryptotolga.model.Coin
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class CoinAPIService {



    private val BASE_URL="https://api.coingecko.com/"
    private val api=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CoinApi::class.java)

    fun getData() : Single<List<Coin>>{
        return api.getAll()
    }


}