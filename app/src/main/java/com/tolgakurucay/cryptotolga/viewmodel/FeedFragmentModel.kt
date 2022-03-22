package com.tolgakurucay.cryptotolga.viewmodel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tolgakurucay.cryptotolga.model.Coin
import com.tolgakurucay.cryptotolga.service.CoinAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class FeedFragmentModel : ViewModel() {

    private val coinApiService=CoinAPIService()
    private val disposable=CompositeDisposable()



    val coins=MutableLiveData<List<Coin>>()
    val error=MutableLiveData<Boolean>()
    val progressBar=MutableLiveData<Boolean>()


    fun getDataFromApi(){
        progressBar.value=true
        disposable.add(
            coinApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Coin>>(){
                    override fun onSuccess(t: List<Coin>) {
                        coins.value=t

                    }

                    override fun onError(e: Throwable) {
                        progressBar.value=false
                        error.value=true
                        e.printStackTrace()
                    }


                })









        )


    }


}