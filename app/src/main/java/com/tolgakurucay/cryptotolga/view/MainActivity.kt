package com.tolgakurucay.cryptotolga.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tolgakurucay.cryptotolga.R
import com.tolgakurucay.cryptotolga.adapter.CoinListAdapter
import com.tolgakurucay.cryptotolga.model.Coin
import com.tolgakurucay.cryptotolga.util.Constants
import com.tolgakurucay.cryptotolga.viewmodel.FeedFragmentModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_feed.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


    }


}