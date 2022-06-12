package com.tolgakurucay.cryptotolga.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tolgakurucay.cryptotolga.model.Coin
import com.tolgakurucay.cryptotolga.service.CoinAPIService
import com.tolgakurucay.cryptotolga.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class FavoritesFragmentModel  : ViewModel() {

    val favoriteList=MutableLiveData<List<Coin>?>()
    val loadingDialog=MutableLiveData<Boolean>()
    val compositeDisposable=CompositeDisposable()
    val apiService=CoinAPIService()




    fun getFavoriteList(currency:String="USDT"){
        loadingDialog.value=true
        val auth=FirebaseAuth.getInstance()
        val firestore=FirebaseFirestore.getInstance()
        var emptyList= listOf<Coin>()
       var idListAsString=""

        auth.currentUser?.let { user->
            firestore.collection("favorites").document(user.uid).collection("favorites").get()
                .addOnSuccessListener {
                    it?.let {
                        for(item in it){
                            idListAsString+="${item.get("coinId")},"

                        }
                        if (idListAsString==""){
                            favoriteList.value=emptyList
                            loadingDialog.value=false
                        }
                        else
                        {
                            compositeDisposable.add(
                                apiService.getFavorites(Constants.API_KEY,currency,idListAsString)
                                    .subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeWith(object : DisposableSingleObserver<List<Coin>>(){
                                        override fun onSuccess(t: List<Coin>) {

                                            favoriteList.value=t
                                            loadingDialog.value=false

                                        }

                                        override fun onError(e: Throwable) {
                                            Log.d("bilgi",e.localizedMessage)
                                            loadingDialog.value=false


                                        }

                                    })
                            )
                        }







                    }
                }
                .addOnFailureListener {
                    loadingDialog.value=false
                }


        }

    }




}