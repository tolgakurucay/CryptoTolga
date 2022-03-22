package com.tolgakurucay.cryptotolga.model

import com.google.gson.annotations.SerializedName


data class Coin(

    @SerializedName("id")
    val id : String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("symbol")
    val symbol: String?,

    @SerializedName("logo_url")
    val url: String?,

    @SerializedName("price")
    val price: String?





)
