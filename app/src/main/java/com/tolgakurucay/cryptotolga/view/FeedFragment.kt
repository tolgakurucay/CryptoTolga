package com.tolgakurucay.cryptotolga.view


import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.tolgakurucay.cryptotolga.R
import com.tolgakurucay.cryptotolga.adapter.CoinListAdapter
import com.tolgakurucay.cryptotolga.databinding.FragmentCoinBinding
import com.tolgakurucay.cryptotolga.databinding.FragmentFeedBinding
import com.tolgakurucay.cryptotolga.model.Coin
import com.tolgakurucay.cryptotolga.util.Constants
import com.tolgakurucay.cryptotolga.viewmodel.FeedFragmentModel

import kotlinx.android.synthetic.main.fragment_feed.*


class FeedFragment : Fragment() {

    private lateinit var viewModel:FeedFragmentModel
    private var adapter = CoinListAdapter(arrayListOf())
    private lateinit var binding:FragmentFeedBinding

     var currency:String="USD"



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentFeedBinding.bind(view)


        coinList.layoutManager=LinearLayoutManager(view.context)
        coinList.adapter=adapter


        viewModel=ViewModelProviders.of(this).get(FeedFragmentModel::class.java)


        viewModel.getDataFilterByCurrency(currency,Constants.API_KEY)




        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing=false
            viewModel.getDataFilterByCurrency(Constants.curr,Constants.API_KEY)





        }

        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){

                R.id.dollar-> dollar()
                R.id.turkish_lira->turkishLira()
                R.id.euro->euro()
                R.id.signOut->signOut()
            }
            true
        }





        binding.editTextCoin.addTextChangedListener {
            viewModel.searchOneCoin(binding.editTextCoin.text.toString())
        }





        observeLiveData()










    }


    private fun dollar(){
        Constants.curr="USD"


        viewModel.getDataFilterByCurrency(Constants.curr, Constants.API_KEY)

    }
    private fun turkishLira(){
        Constants.curr="TRY"

        viewModel.getDataFilterByCurrency(Constants.curr, Constants.API_KEY)

    }
    private fun euro(){
        Constants.curr="EUR"

        viewModel.getDataFilterByCurrency(Constants.curr, Constants.API_KEY)

    }

    private fun signOut(){

        val auth=FirebaseAuth.getInstance()

        if(auth.currentUser!=null){

            val alertDialog=AlertDialog.Builder(this.requireContext())

            alertDialog.setTitle("Çıkış İşlemi")
            alertDialog.setMessage("Çıkış Yapmak İstediğinize Emin Misiniz?")
            alertDialog.setPositiveButton("Evet"){a,b->

                auth.signOut()
                val action=FeedFragmentDirections.actionFeedFragmentToLoginFragment()
                Navigation.findNavController(this.requireView()).navigate(action)
                this.requireParentFragment().onStop()

            }
            alertDialog.create().show()





        }

    }



    private fun observeLiveData(){

        viewModel.searchedCoins.observe(viewLifecycleOwner, Observer {

            if(it!=null){
                adapter.updateCoinList(it)
            }


        })





        viewModel.coins.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {
                coinList.visibility=View.VISIBLE
                //burda adapter içi doldurulacakw
                adapter.updateCoinList(it)


            }


        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it==true){
                    coinError.visibility=View.VISIBLE
                    coinList.visibility=View.GONE
                    progressBar.visibility=View.GONE
                }
                else
                {
                    coinError.visibility=View.GONE
                }
            }
        })

        viewModel.progressBar.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it==true){
                    progressBar.visibility=View.VISIBLE
                    coinList.visibility=View.GONE
                    coinError.visibility=View.GONE

                }
                else
                {
                    progressBar.visibility=View.GONE
                }
            }

        })


    }

    override fun onDestroy() {
        super.onDestroy()
    }













}

