package com.tolgakurucay.cryptotolga.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Coin(

    @SerializedName("name")
    @ColumnInfo(name="name")
    val coinName:String?,

    @SerializedName("currency")
    @ColumnInfo(name="currency")
    val name: String?,

    @SerializedName("price")
    @ColumnInfo(name="price")
    val price: String?,

    @SerializedName("id")
    @ColumnInfo(name="id")
    val id : String?,

    @SerializedName("symbol")
    @ColumnInfo(name="symbol")
    val symbol: String?,

    @SerializedName("logo_url")
    @ColumnInfo(name="logo_url")
    val url: String?,

    @SerializedName("status")
    @ColumnInfo(name="status")
    val status: String?,

    @SerializedName("price_date")
    @ColumnInfo(name="price_date")
    val priceDate: String?,

    @SerializedName("price_timestamp")
    @ColumnInfo(name="price_timestamp")
    val priceTimeStamp: String?,

    @SerializedName("circulating_supply")
    @ColumnInfo(name="circulating_supply")
    val circulatingSupply: String?,

    @SerializedName("max_supply")
    @ColumnInfo(name="max_supply")
    val maxSupply: String?,

    @SerializedName("market_cap")
    @ColumnInfo(name="market_cap")
    val marketCap: String?,

    @SerializedName("market_cap_dominance")
    @ColumnInfo(name="market_cap_dominance")
    val marketCapDominance : String?,

    @SerializedName("transparent_market_cap")
    @ColumnInfo(name="transparent_market_cap")
    val transparentMarketCap: String?,

    @SerializedName("rank")
    @ColumnInfo(name="rank")
    val rank:String?













){
    @PrimaryKey(autoGenerate = true)
    var databaseID:Int=0
}
