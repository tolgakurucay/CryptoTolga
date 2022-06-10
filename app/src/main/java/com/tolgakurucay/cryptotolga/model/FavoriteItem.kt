package com.tolgakurucay.cryptotolga.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



data class FavoriteItem(

    @ColumnInfo(name = "coinId")
    val coinId:String

    ) {


}