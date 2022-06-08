package com.tolgakurucay.cryptotolga.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorites")
data class FavoriteItem(

    @ColumnInfo(name = "coinId")
    val coinId:String

    ) {

    @PrimaryKey(autoGenerate = true)
    var id:Int=0
}