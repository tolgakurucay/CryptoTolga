package com.tolgakurucay.cryptotolga.service

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.tolgakurucay.cryptotolga.model.Coin

@Dao
interface CoinDAO {

    @Insert
    suspend fun insertAll(vararg coins:Coin): List<Long>

    @Query("SELECT * from Coin")
    suspend fun getAllCoins() : List<Coin>

    @Query("Delete from Coin")
    suspend fun deleteAllCoins()

    @Query("Select * from Coin where name like '%'|| :coinName ||'%'  or currency like '%'|| :coinCode ||'%'")
    suspend fun selectOneCoin(coinName:String,coinCode:String) : List<Coin>









}