package com.tolgakurucay.cryptotolga.view

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.icu.lang.UCharacter
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tolgakurucay.cryptotolga.R
import com.tolgakurucay.cryptotolga.adapter.CoinListAdapter
import com.tolgakurucay.cryptotolga.adapter.FavoritesAdapter
import com.tolgakurucay.cryptotolga.databinding.FragmentFavoritesBinding
import com.tolgakurucay.cryptotolga.model.Coin
import com.tolgakurucay.cryptotolga.service.CoinAPIService
import com.tolgakurucay.cryptotolga.util.Constants
import com.tolgakurucay.cryptotolga.view.activities.EntryActivity
import com.tolgakurucay.cryptotolga.viewmodel.FavoritesFragmentModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers


class FavoritesFragment : Fragment() {


    private lateinit var auth:FirebaseAuth


    private lateinit var binding:FragmentFavoritesBinding

    private lateinit var viewModel:FavoritesFragmentModel

    private lateinit var adapter : FavoritesAdapter


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

       setup(view)




        if(auth.currentUser==null){
           val intent= Intent(this.requireContext(),EntryActivity::class.java)
            startActivity(intent)

        }
        else
        {


            viewModel.getFavoriteList(Constants.curr)












            observeLiveData()

        }






    }

    private fun observeLiveData() {
        viewModel.favoriteList.observe(viewLifecycleOwner, Observer {
            it?.let { list->
                adapter.updateCoinList(list)
                for(item in list){
                    Log.d("bilgi",item.toString())
                }


            }
        })

        viewModel.loadingDialog.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){

                    binding.recyclerFavorites.visibility=View.INVISIBLE
                    binding.progressBarFavorites.visibility=View.VISIBLE
                }
                else
                {
                    binding.recyclerFavorites.visibility=View.VISIBLE
                    binding.progressBarFavorites.visibility=View.INVISIBLE
                }
            }
        })

    }
    private fun setup(view:View){
        viewModel=ViewModelProvider(this).get(FavoritesFragmentModel::class.java)
        auth= FirebaseAuth.getInstance()
        binding= FragmentFavoritesBinding.bind(view)
        adapter= FavoritesAdapter(arrayListOf(),findNavController())

        binding.recyclerFavorites.layoutManager=LinearLayoutManager(this.requireContext())
        binding.recyclerFavorites.adapter=adapter




    }



}