package com.tolgakurucay.cryptotolga.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivityModel : ViewModel() {

val mailAndName=MutableLiveData<ArrayList<String>>()

    fun getMailAndName(){
        var arrayList= arrayListOf<String>()
        val auth=FirebaseAuth.getInstance()
        val firestore=FirebaseFirestore.getInstance()

        auth.currentUser?.let { user->
            firestore.collection("users").document(user.uid).get()
                .addOnSuccessListener {
                    it?.let {
                        arrayList.add(it["name"].toString())
                        arrayList.add(it["mail"].toString())
                        mailAndName.value=arrayList
                    }
                }

        }
    }










}