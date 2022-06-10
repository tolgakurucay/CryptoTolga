package com.tolgakurucay.cryptotolga.view

import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.ViewGroupUtils
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.tolgakurucay.cryptotolga.R
import com.tolgakurucay.cryptotolga.adapter.CoinListAdapter
import com.tolgakurucay.cryptotolga.databinding.ActivityMainBinding
import com.tolgakurucay.cryptotolga.databinding.NavHeaderBinding
import com.tolgakurucay.cryptotolga.model.Coin
import com.tolgakurucay.cryptotolga.util.Constants
import com.tolgakurucay.cryptotolga.viewmodel.FeedFragmentModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.android.synthetic.main.nav_header.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var toggle:ActionBarDrawerToggle
    private lateinit var binding:ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)








        toggle= ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.isDrawerIndicatorEnabled=true
        toggle.isDrawerSlideAnimationEnabled=true




        binding.navView.setNavigationItemSelectedListener { menuItem->


            when(menuItem.itemId){

                R.id.logout->signOut()
                R.id.turkish->Toast.makeText(applicationContext,"Yapım Aşamasında",Toast.LENGTH_SHORT).show()
                R.id.favorites->replaceFragment(FavoritesFragment())
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true


        }






        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true

        }
        return super.onOptionsItemSelected(item)
    }
    private fun signOut(){
        val auth=FirebaseAuth.getInstance()

        if(auth.currentUser!=null){

            val alertDialog= AlertDialog.Builder(this)

            alertDialog.setTitle("Çıkış İşlemi")
            alertDialog.setMessage("Çıkış Yapmak İstediğinize Emin Misiniz?")
            alertDialog.setPositiveButton("Evet"){a,b->

                auth.signOut()
                val intent=Intent(this@MainActivity,EntryActivity::class.java)
                startActivity(intent)
                this.finish()


            }
            alertDialog.setNegativeButton("Hayır"){_,_->
                //nothing

            }
            alertDialog.setIcon(R.drawable.logout)
            alertDialog.create().show()






        }
    }

    private fun replaceFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,fragment).commit()
    }















}