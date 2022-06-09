package com.tolgakurucay.cryptotolga.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.tolgakurucay.cryptotolga.R
import com.tolgakurucay.cryptotolga.adapter.CoinListAdapter
import com.tolgakurucay.cryptotolga.databinding.ActivityEntryBinding
import com.tolgakurucay.cryptotolga.databinding.ActivityMainBinding
import com.tolgakurucay.cryptotolga.model.Coin
import com.tolgakurucay.cryptotolga.util.Constants
import com.tolgakurucay.cryptotolga.viewmodel.FeedFragmentModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_feed.*

class EntryActivity : AppCompatActivity() {


    private lateinit var binding:ActivityEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val auth= FirebaseAuth.getInstance()
        if(auth.currentUser!=null){
            val intent=Intent(this@EntryActivity,MainActivity::class.java)
            startActivity(intent)
        }







    }













}