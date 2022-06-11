package com.tolgakurucay.cryptotolga.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.tolgakurucay.cryptotolga.databinding.ActivityEntryBinding
import android.view.View

class EntryActivity : AppCompatActivity() {


    private lateinit var binding:ActivityEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fragmentContainerView.visibility=View.INVISIBLE

        val auth= FirebaseAuth.getInstance()
        if(auth.currentUser!=null){
            val intent=Intent(this@EntryActivity, MainActivity::class.java)
            startActivity(intent)
        }
        else
        {
            binding.fragmentContainerView.visibility=View.VISIBLE
        }







    }













}