package com.tolgakurucay.cryptotolga.viewmodel

import android.app.Application
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.MutableLiveData
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

import com.tolgakurucay.cryptotolga.databinding.FragmentCoinBinding

import com.tolgakurucay.cryptotolga.model.Coin
import com.tolgakurucay.cryptotolga.model.FavoriteItem
import com.tolgakurucay.cryptotolga.service.CoinAPIService
import com.tolgakurucay.cryptotolga.service.CoinDAO
import com.tolgakurucay.cryptotolga.service.CoinDatabase
import com.tolgakurucay.cryptotolga.util.Constants

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch


class CoinFragmentModel(application:Application) : BaseViewModel(application) {

     var coin = MutableLiveData<List<Coin>>()
    var loading=MutableLiveData<Boolean>()
    var addedToFavorites=MutableLiveData<Boolean>()
    var isItemFavorited=MutableLiveData<Boolean>()

    private val coinApiService=CoinAPIService()
   private val disposable=CompositeDisposable()





    fun getSingleDataFromAPI(id:String,currency:String,layoutInflater: LayoutInflater) {


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

    fun addToFavorites(id:String){
        val auth=FirebaseAuth.getInstance()
        val firestore=FirebaseFirestore.getInstance()

        auth.currentUser?.let {

            var model=FavoriteItem(id)
            firestore.collection("favorites").document(it.uid).collection("favorites").add(model)
                .addOnSuccessListener { addedToFavorites.value=true }
                .addOnFailureListener { addedToFavorites.value=false }



        }


    }

    fun isItemFavorited(id:String){
        val auth=FirebaseAuth.getInstance()
        val firestore=FirebaseFirestore.getInstance()

        auth.currentUser?.let {

            firestore.collection("favorites").document(it.uid).collection("favorites").whereEqualTo("coinId",id).get()
                .addOnSuccessListener { isItemFavorited.value=true }
                .addOnFailureListener { isItemFavorited.value=false }




        }

    }





    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }





}


