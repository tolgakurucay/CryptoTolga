package com.tolgakurucay.cryptotolga.service

import android.text.method.SingleLineTransformationMethod
import com.tolgakurucay.cryptotolga.model.Coin
import io.reactivex.Observer
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class CoinAPIService {



    private val BASE_URL="https://api.nomics.com/v1/"
    private val api=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CoinApi::class.java)



      fun getSingleData(key : String, ids:String, currency:String ) : Single<List<Coin>>{

        return api.getCrypto(key,ids,currency)
    }


    fun getDataFilterByCurrency(key:String,currency:String) : Single<List<Coin>>{
        return api.getCryptoFilterByCurrency(key,currency)
    }

    fun getFavorites(key:String,currency: String,idList:String) : Single<List<Coin>>{
        return api.getFavorites(key,idList,currency)

    }


}