package com.tolgakurucay.cryptotolga.viewmodel

import android.app.Application
import android.os.CountDownTimer
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tolgakurucay.cryptotolga.model.Coin
import com.tolgakurucay.cryptotolga.service.CoinAPIService
import com.tolgakurucay.cryptotolga.service.CoinDatabase
import com.tolgakurucay.cryptotolga.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import java.util.*

class FeedFragmentModel(application: Application) : BaseViewModel(application) {

    private val coinApiService = CoinAPIService()
    private val disposable = CompositeDisposable()


    val coins = MutableLiveData<List<Coin>>()
    val searchedCoins =MutableLiveData<List<Coin>>()
    val error = MutableLiveData<Boolean>()
    val progressBar = MutableLiveData<Boolean>()





    fun getDataFilterByCurrency(language:String,apiKey:String){
        progressBar.value=true
        disposable.add(


            coinApiService.getDataFilterByCurrency(apiKey,language)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Coin>>(){
                    override fun onSuccess(t: List<Coin>) {
                       coins.value=t
                        storeInSQLite(t)
                        println(t.size)
                        progressBar.value=false
                    }

                    override fun onError(e: Throwable) {
                        error.value=true
                        e.printStackTrace()
                        progressBar.value=false
                    }

                })

        )

    }

    fun getFavorites(language:String,apiKey:String){
        progressBar.value=true
        disposable.add(


            coinApiService.getDataFilterByCurrency(apiKey,language)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Coin>>(){
                    override fun onSuccess(t: List<Coin>) {
                        coins.value=t
                        storeInSQLite(t)
                        println(t.size)
                        progressBar.value=false
                    }

                    override fun onError(e: Throwable) {
                        error.value=true
                        e.printStackTrace()
                        progressBar.value=false
                    }

                })

        )

    }



    fun storeInSQLite(coinList:List<Coin>){
        launch {
            val dao=CoinDatabase(getApplication()).coinDao()
            dao.deleteAllCoins()

            val listLong=dao.insertAll(*coinList.toTypedArray())

            for(i in 0 until (coinList.size-1)){
                coinList[i].databaseID=listLong[i].toInt()
            }
            showCoins(coinList)
        }

    }

    fun searchOneCoin(coinName:String){
        launch {
            val dao=CoinDatabase(getApplication()).coinDao()
           searchedCoins.value=dao.selectOneCoin(coinName.capitalize(),coinName.uppercase())


        }

    }

    private fun showCoins(coinList: List<Coin>) {
        coins.value=coinList
        error.value=false
        progressBar.value=false
    }


    override fun onCleared() {

        disposable.clear()
    }




}