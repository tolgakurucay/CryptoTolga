package com.tolgakurucay.cryptotolga.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class LoginFragmentModel(application: Application): BaseViewModel(application) {


    var bool=MutableLiveData<Boolean>()

    fun signIn(email:String,password:String){



        launch {


                val auth=FirebaseAuth.getInstance()
                auth.signInWithEmailAndPassword(email,password)
                    .addOnSuccessListener {
                        bool.value=true
                    }
                    .addOnFailureListener {
                        bool.value=false
                    }




        }






    }














}