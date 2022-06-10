package com.tolgakurucay.cryptotolga.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tolgakurucay.cryptotolga.R


class FavoritesFragment : Fragment() {


    private lateinit var auth:FirebaseAuth
    private lateinit var firestore:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        auth= FirebaseAuth.getInstance()
        if(auth.currentUser==null){
            val action=FavoritesFragmentDirections.actionFavoritesFragmentToFeedFragment()
            Navigation.findNavController(view).navigate(action)

        }
        firestore= FirebaseFirestore.getInstance()

        firestore.collection("favorites").document(auth.uid.toString()).collection("favorites")
            .get()
            .addOnSuccessListener {
                it?.let { ss->

                    for(fav in ss){
                       Log.d("bilgi",fav.get("coinId").toString())
                    }

                    //val favo=ss.documents

                }
            }


    }


}