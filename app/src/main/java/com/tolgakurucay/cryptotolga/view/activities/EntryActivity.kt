package com.tolgakurucay.cryptotolga.view.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.tolgakurucay.cryptotolga.databinding.ActivityEntryBinding
import android.view.View
import com.tolgakurucay.cryptotolga.R

class EntryActivity : AppCompatActivity() {


    private lateinit var binding:ActivityEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fragmentContainerView.visibility=View.INVISIBLE
        binding.entryBackground.visibility=View.VISIBLE
        binding.constraint.setBackgroundColor(resources.getColor(R.color.entryBackgroundColor))

        setup()











    }

    private fun setup(){
        object : CountDownTimer(2000,1000){
            override fun onTick(p0: Long) {
                Log.d("bilgi",p0.toString())
            }

            override fun onFinish() {
                Log.d("bilgi","finish")
                val auth= FirebaseAuth.getInstance()
                if(auth.currentUser!=null && auth.currentUser!!.isEmailVerified){
                    val intent=Intent(this@EntryActivity, MainActivity::class.java)

                    startActivity(intent)
                    this@EntryActivity.finish()
                }
                else
                {
                    binding.entryBackground.visibility=View.INVISIBLE
                    binding.fragmentContainerView.visibility=View.VISIBLE
                }
            }

        }.start()
    }













}