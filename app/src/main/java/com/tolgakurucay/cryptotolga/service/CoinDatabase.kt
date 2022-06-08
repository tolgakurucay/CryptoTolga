package com.tolgakurucay.cryptotolga.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tolgakurucay.cryptotolga.model.Coin
import com.tolgakurucay.cryptotolga.model.FavoriteItem


@Database(entities = arrayOf(Coin::class), version = 1)
abstract class CoinDatabase :RoomDatabase(){

    abstract fun coinDao() : CoinDAO

    companion object{
        @Volatile private var instance:CoinDatabase?=null

        private val lock=Any()

        operator fun invoke(context:Context)= instance?: synchronized(lock){
            instance ?: makeDatabase(context).also {
                instance=it
            }
        }

        private fun makeDatabase(context: Context)=
            Room.databaseBuilder(context.applicationContext,CoinDatabase::class.java,"CoinDatabase")
                .build()



    }
}