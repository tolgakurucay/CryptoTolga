package com.tolgakurucay.cryptotolga.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.tolgakurucay.cryptotolga.R
import com.tolgakurucay.cryptotolga.databinding.FragmentCoinBinding
import com.tolgakurucay.cryptotolga.util.Constants
import com.tolgakurucay.cryptotolga.view.activities.EntryActivity
import com.tolgakurucay.cryptotolga.viewmodel.CoinFragmentModel


class CoinFragment : Fragment() {

    private lateinit var viewModel:CoinFragmentModel

    private var id : String=""
    private lateinit var dataBinding:FragmentCoinBinding
    private lateinit var auth:FirebaseAuth

      var currency:String="TRY"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth= FirebaseAuth.getInstance()
        if(auth.currentUser==null){
            val intent= Intent(activity, EntryActivity::class.java)
            startActivity(intent)
        }
        arguments?.let {
            id=CoinFragmentArgs.fromBundle(it).coinId

            currency=CoinFragmentArgs.fromBundle(it).currency
        }
        viewModel=ViewModelProviders.of(this@CoinFragment).get(CoinFragmentModel::class.java)
        viewModel.isItemFavorited(id)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

       dataBinding =DataBindingUtil.inflate(inflater,R.layout.fragment_coin,container,false)





        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        activity?.let {
            activity?.let {
                it.setTitle(id)
            }
        }

        language(Constants.curr)

        arguments?.let {



        }

        viewModel.getSingleDataFromAPI(id,currency,layoutInflater)


        dataBinding.buttonFavorites.setOnClickListener {
           // viewModel.addToFavorites(id)
            viewModel.addToFavorites(id)
        }
        dataBinding.buttonProfitAndLoss.setOnClickListener {
            viewModel.isItemFavorited(id)
        }














        observeLiveData()


    }

    private fun language(languageCode:String){
        /*if(languageCode=="TRY")
        {
            dataBinding.coinCodeTextView.setText(R.string.codeTR)
            dataBinding.coinNameTextView.setText(R.string.nameTR)
            dataBinding.coinMarketCapTextView.setText(R.string.marketCapTR)
            dataBinding.coinPriceTextView.setText(R.string.priceTR)
            dataBinding.coinStatusTextView.setText(R.string.statusTR)
            dataBinding.coinRankTextView.setText(R.string.rankTR)
            dataBinding.coinDateTextView.setText(R.string.dateTR)
            dataBinding.buttonFavorites.setText(R.string.addFavoritesTR)
            dataBinding.buttonProfitAndLoss.setText(R.string.profitAndLossTR)
        }
        else
        {*/
            dataBinding.coinCodeTextView.setText(R.string.codeEN)
            dataBinding.coinNameTextView.setText(R.string.nameEN)
            dataBinding.coinMarketCapTextView.setText(R.string.marketCapEN)
            dataBinding.coinPriceTextView.setText(R.string.priceEN)
            dataBinding.coinStatusTextView.setText(R.string.statusEN)
            dataBinding.coinRankTextView.setText(R.string.rankEN)
            dataBinding.coinDateTextView.setText(R.string.dateEN)
            dataBinding.buttonFavorites.setText(R.string.addFavoritesEN)
            dataBinding.buttonProfitAndLoss.setText(R.string.profitAndLossEN)
        //}
    }

    private fun observeLiveData(){



        viewModel.isItemFavorited.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){
                    //db'de var
                    Log.d("bilgi",it.toString())
                    dataBinding.buttonFavorites.setText("Remove From Favorites")
                }
                else
                {
                    Log.d("bilgi",it.toString())
                    dataBinding.buttonFavorites.setText("Add To Favorites")
                }
            }
        })

        viewModel.addedToFavorites.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){
                    Log.d("bilgi","Favorilere Eklendi")
                    dataBinding.buttonFavorites.setText("Remove from Favorites")

                }
                else
                {
                    Log.d("bilgi","Favorilerden silindi")
                    dataBinding.buttonFavorites.setText("Add to Favorites")
                }
            }
        })




        viewModel.coin.observe(viewLifecycleOwner, Observer {
            it?.let {


                    dataBinding.coin= it[0]


            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it==true){

                    dataBinding.constraintLay.visibility=View.GONE

                    dataBinding.selectedCryptoLoading.visibility=View.VISIBLE



                }
                else
                {

                    dataBinding.constraintLay.visibility=View.VISIBLE

                    dataBinding.selectedCryptoLoading.visibility=View.GONE


                }
            }
        })




    }
    override fun onDestroy() {
        super.onDestroy()
    }


}