package com.tolgakurucay.cryptotolga.view.activities

import android.app.AlertDialog
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.tolgakurucay.cryptotolga.R
import com.tolgakurucay.cryptotolga.databinding.ActivityMainBinding
import com.tolgakurucay.cryptotolga.view.*
import com.tolgakurucay.cryptotolga.viewmodel.MainActivityModel
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var toggle:ActionBarDrawerToggle
    private lateinit var binding:ActivityMainBinding
    private lateinit var viewModel:MainActivityModel

    private lateinit var header:View
    private lateinit var textMail:TextView
    private lateinit var textName:TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar?.let {
            it.setDisplayShowCustomEnabled(true)




        }
        val toolbar=findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)



        viewModel=ViewModelProvider(this).get(MainActivityModel::class.java)
        setup()

        viewModel.getMailAndName()





        toggle= ActionBarDrawerToggle(this,binding.drawerLayout,toolbar,R.string.open,R.string.close)

        binding.drawerLayout.addDrawerListener(toggle)


        toggle.syncState()



        binding.navView.setNavigationItemSelectedListener { menuItem->


            when(menuItem.itemId){

                R.id.logout->signOut()
                R.id.turkish->setLocale("tr")
                R.id.english->setLocale("en")
                R.id.favorites->replaceFragment()
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true


        }



    observeLiveData()


        }
    fun setLocale(lang: String?) {
        val myLocale = Locale(lang)
        val res: Resources = resources
        val dm: DisplayMetrics = res.getDisplayMetrics()
        val conf: Configuration = res.getConfiguration()
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
        val refresh = Intent(this, MainActivity::class.java)
        startActivity(refresh)
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

            alertDialog.setTitle(R.string.exit)
            alertDialog.setMessage(R.string.exitDesc)
            alertDialog.setPositiveButton(R.string.yes){a,b->

                auth.signOut()
                val intent=Intent(this@MainActivity, EntryActivity::class.java)
                startActivity(intent)
                this.finish()


            }
            alertDialog.setNegativeButton(R.string.no){_,_->
                //nothing

            }
            alertDialog.setIcon(R.drawable.logout)
            alertDialog.create().show()






        }
    }

    private fun replaceFragment(){




        val navHost=supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController=navHost.navController

        navController.currentDestination?.let {
            if(navController.currentDestination!!.displayName.equals("com.tolgakurucay.cryptotolga:id/coinFragment")){
                navController.navigate(CoinFragmentDirections.actionCoinFragmentToFavoritesFragment())
            }
            else if(navController.currentDestination!!.displayName.equals("com.tolgakurucay.cryptotolga:id/feedFragment")){
                navController.navigate(FeedFragmentDirections.actionFeedFragmentToFavoritesFragment())
            }
        }



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

    private fun setup(){

        header=binding.navView.getHeaderView(0)
        textName=header.findViewById<TextView>(R.id.nameTextView)
        textMail=header.findViewById<TextView>(R.id.mailTextView)




    }

    private fun observeLiveData(){

        viewModel.mailAndName.observe(this, Observer {
            it?.let {
                textName.setText(it[0])
                textMail.setText(it[1])
            }
        })



    }























}