package com.tolgakurucay.cryptotolga.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordFragmentModel(application: Application) : BaseViewModel(application) {

    val isMailTrue=MutableLiveData<Boolean>()

    fun forgotPassword(mail:String){
        val auth=FirebaseAuth.getInstance()
        auth.sendPasswordResetEmail(mail)
            .addOnSuccessListener {
                isMailTrue.value=true
            }
            .addOnFailureListener {
                isMailTrue.value=false
            }
    }





}