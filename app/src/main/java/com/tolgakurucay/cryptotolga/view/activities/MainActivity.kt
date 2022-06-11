package com.tolgakurucay.cryptotolga.view.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.tolgakurucay.cryptotolga.R
import com.tolgakurucay.cryptotolga.databinding.ActivityMainBinding
import com.tolgakurucay.cryptotolga.view.FavoritesFragment

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

    private fun replaceFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,fragment).commit()
    }















}