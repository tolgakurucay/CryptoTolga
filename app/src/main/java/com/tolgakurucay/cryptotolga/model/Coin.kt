package com.tolgakurucay.cryptotolga.model

import com.google.gson.annotations.SerializedName


data class Coin(

    @SerializedName("name")
    val coinName:String?,

    @SerializedName("currency")
    val name: String?,

    @SerializedName("price")
    val price: String?,

    @SerializedName("id")
    val id : String?,

    @SerializedName("symbol")
    val symbol: String?,

    @SerializedName("logo_url")
    val url: String?,

    @SerializedName("status")
    val status: String?,

    @SerializedName("price_date")
    val priceDate: String?,

    @SerializedName("price_timestamp")
    val priceTimeStamp: String?,

    @SerializedName("circulating_supply")
    val circulatingSupply: String?,

    @SerializedName("max_supply")
    val maxSupply: String?,

    @SerializedName("market_cap")
    val marketCap: String?,

    @SerializedName("market_cap_dominance")
    val marketCapDominance : String?,

    @SerializedName("transparent_market_cap")
    val transparentMarketCap: String?,

    @SerializedName("rank")
    val rank:String?













)
