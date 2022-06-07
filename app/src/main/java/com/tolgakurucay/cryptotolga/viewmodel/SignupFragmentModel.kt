package com.tolgakurucay.cryptotolga.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


class SignupFragmentModel(application: Application) : BaseViewModel(application)
{

    val signUp=MutableLiveData<Boolean>()

    fun signup(name:String,email:String,password:String) {
        val auth=FirebaseAuth.getInstance()


        launch {
            auth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener {
                    signUp.value=true
                }
                .addOnFailureListener {
                    signUp.value=false
                }

        }


    }

}