package com.tolgakurucay.cryptotolga.service

import com.tolgakurucay.cryptotolga.model.Coin
import io.reactivex.Observer
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinApi {

    //BASE URL
    //https://api.nomics.com/v1/

    //EXTENSION URL
    //https://api.nomics.com/v1/currencies/ticker?key=your-key-here&ids=BTC,ETH,XRP&interval=1d,30d&convert=EUR&platform-currency=ETH&per-page=100&page=1
    //2f10fb4972dc05c2ccc090f70b02bf49719edfba

    @GET("currencies/ticker")
       fun getAll(
        @Query("key") key : String,

     ) : Single<List<Coin>>



    @GET("currencies/ticker")
      fun getCrypto(
        @Query("key") key : String,
        @Query("ids") name: String,
        @Query("convert") currency: String,

    ) : Single<List<Coin>>



      @GET("currencies/ticker")
      fun getCryptoFilterByCurrency(
          @Query("key") key : String,
          @Query("convert") currency : String,
      ) : Single<List<Coin>>





}