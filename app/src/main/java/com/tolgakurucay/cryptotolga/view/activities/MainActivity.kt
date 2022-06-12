package com.tolgakurucay.cryptotolga.view.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.tolgakurucay.cryptotolga.R
import com.tolgakurucay.cryptotolga.databinding.ActivityMainBinding
import com.tolgakurucay.cryptotolga.databinding.NavHeaderBinding
import com.tolgakurucay.cryptotolga.model.NavigationViewProfile
import com.tolgakurucay.cryptotolga.view.*


class MainActivity : AppCompatActivity() {

    private lateinit var toggle:ActionBarDrawerToggle
    private lateinit var binding:ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /*val nav=NavigationViewProfile("sad","asadsdasd","asdasda")
        val dataBinding:NavHeaderBinding=DataBindingUtil.setContentView(this@MainActivity,R.layout.nav_header)
        dataBinding.navigationViewProfile=nav*/












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
                R.id.favorites->replaceFragment(FeedFragmentDirections.actionFeedFragmentToFavoritesFragment())
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
                val intent=Intent(this@MainActivity, EntryActivity::class.java)
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

    private fun replaceFragment(navDirections: NavDirections){



        val navHost=supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController=navHost.navController
        navController.navigate(navDirections)

    }var doubleBackPress=false

    override fun onBackPressed() {
        val navHost=supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController=navHost.navController

        navController.currentDestination?.let {
            if(navController.currentDestination!!.displayName.equals("com.tolgakurucay.cryptotolga:id/coinFragment")){
                navController.navigate(CoinFragmentDirections.actionCoinFragmentToFeedFragment())
            }
            else if(navController.currentDestination!!.displayName.equals("com.tolgakurucay.cryptotolga:id/favoritesFragment")){
                navController.navigate(FavoritesFragmentDirections.actionFavoritesFragmentToFeedFragment())
            }

            else if(navController.currentDestination!!.displayName.equals("com.tolgakurucay.cryptotolga:id/feedFragment")){

                if(doubleBackPress){
                       val intent=Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_HOME)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)

                }
                doubleBackPress=true
                Log.d("bilgi",doubleBackPress.toString())
                  //  Toast.makeText(this,"Please click BACK again to exit",Toast.LENGTH_SHORT).show()
                    Handler(Looper.getMainLooper()).postDelayed(Runnable {doubleBackPress=false  },2000)


            }
        }




       // Log.d("bilgi",navController.currentDestination!!.displayName.toString())


    }





















}