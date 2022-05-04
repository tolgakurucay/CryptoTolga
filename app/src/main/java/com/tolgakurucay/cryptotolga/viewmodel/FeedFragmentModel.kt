package com.tolgakurucay.cryptotolga.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tolgakurucay.cryptotolga.model.Coin
import com.tolgakurucay.cryptotolga.service.CoinAPIService
import com.tolgakurucay.cryptotolga.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class FeedFragmentModel(application: Application) : BaseViewModel(application) {

    private val coinApiService = CoinAPIService()
    private val disposable = CompositeDisposable()


    val coins = MutableLiveData<List<Coin>>()
    val error = MutableLiveData<Boolean>()
    val progressBar = MutableLiveData<Boolean>()


    fun getDataFromApi() {
        progressBar.value = true


       disposable.add(


              coinApiService.getData(Constants.API_KEY)
                   .subscribeOn(Schedulers.newThread())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribeWith(object : DisposableSingleObserver<List<Coin>>() {
                       override fun onSuccess(t: List<Coin>) {

                           coins.value = t
                           progressBar.value = false


                       }

                       override fun onError(e: Throwable) {

                           progressBar.value = false
                           error.value = true

                       }

                   })


       )

    }


    fun getDataFilterByCurrency(language:String,apiKey:String){
        progressBar.value=true
        disposable.add(


            coinApiService.getDataFilterByCurrency(apiKey,language)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Coin>>(){
                    override fun onSuccess(t: List<Coin>) {
                       coins.value=t
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

    override fun onCleared() {

        disposable.clear()
    }


}