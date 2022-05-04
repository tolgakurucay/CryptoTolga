package com.tolgakurucay.cryptotolga.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData

import com.tolgakurucay.cryptotolga.model.Coin
import com.tolgakurucay.cryptotolga.service.CoinAPIService
import com.tolgakurucay.cryptotolga.util.Constants

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers




class CoinFragmentModel(application:Application) : BaseViewModel(application) {

     var coin = MutableLiveData<List<Coin>>()
    var loading=MutableLiveData<Boolean>()
    private val coinApiService=CoinAPIService()
   private val disposable=CompositeDisposable()




    fun getSingleDataFromAPI(id:String,currency:String){


        loading.value=true

           disposable.add(



                coinApiService.getSingleData(Constants.API_KEY,id,currency)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<Coin>>(){


                        override fun onError(e: Throwable) {

                            e.printStackTrace()
                            loading.value=false



                        }

                        override fun onSuccess(t: List<Coin>) {
                            coin.value=t
                            loading.value=false
                        }
                    })

            )





    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }



}


