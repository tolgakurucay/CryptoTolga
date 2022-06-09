package com.tolgakurucay.cryptotolga.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.tolgakurucay.cryptotolga.model.CreatedUserModel
import com.tolgakurucay.cryptotolga.util.Constants
import kotlinx.coroutines.launch


class SignupFragmentModel(application: Application) : BaseViewModel(application)
{

    val signUp=MutableLiveData<Boolean>()

    fun signup(name:String,email:String,password:String) {
        val auth=FirebaseAuth.getInstance()
        val firestore=FirebaseFirestore.getInstance()


        launch {


            auth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener {

                    auth.currentUser!!.sendEmailVerification()
                        .addOnSuccessListener {
                            signUp.value=true
                            Constants.name=name
                            val newUser=CreatedUserModel(name,email,password)
                            firestore.collection("users").document(auth.currentUser!!.uid).set(newUser)//ALWAYS TRUE
                        }
                        .addOnFailureListener {
                            signUp.value=false
                        }







                }
                .addOnFailureListener {
                    signUp.value=false
                }



        }


    }

}