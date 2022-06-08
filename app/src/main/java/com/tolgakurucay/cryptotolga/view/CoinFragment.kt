package com.tolgakurucay.cryptotolga.view

import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat.Token.fromBundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.Person.fromBundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.media.AudioAttributesCompat.fromBundle
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import com.google.firebase.auth.FirebaseAuth
import com.tolgakurucay.cryptotolga.R
import com.tolgakurucay.cryptotolga.databinding.FragmentCoinBinding
import com.tolgakurucay.cryptotolga.util.Constants
import com.tolgakurucay.cryptotolga.viewmodel.CoinFragmentModel
import kotlinx.android.synthetic.main.fragment_feed.*
import java.util.zip.Inflater


class CoinFragment : Fragment() {

    private lateinit var viewModel:CoinFragmentModel
    private var id : String=""
    private lateinit var dataBinding:FragmentCoinBinding
    private lateinit var auth:FirebaseAuth

      var currency:String="TRY"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        auth= FirebaseAuth.getInstance()
        if(auth.currentUser==null){
            val action=CoinFragmentDirections.actionCoinFragmentToLoginFragment()
            Navigation.findNavController(this@CoinFragment.requireView()).navigate(action)
        }

        language(Constants.curr)

        arguments?.let {
            id=CoinFragmentArgs.fromBundle(it).coinId

            currency=CoinFragmentArgs.fromBundle(it).currency


        }
        viewModel=ViewModelProviders.of(this@CoinFragment).get(CoinFragmentModel::class.java)
        viewModel.getSingleDataFromAPI(id,currency,layoutInflater)














        observeLiveData()


    }

    private fun language(languageCode:String){
        if(languageCode=="TRY")
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
        {
            dataBinding.coinCodeTextView.setText(R.string.codeEN)
            dataBinding.coinNameTextView.setText(R.string.nameEN)
            dataBinding.coinMarketCapTextView.setText(R.string.marketCapEN)
            dataBinding.coinPriceTextView.setText(R.string.priceEN)
            dataBinding.coinStatusTextView.setText(R.string.statusEN)
            dataBinding.coinRankTextView.setText(R.string.rankEN)
            dataBinding.coinDateTextView.setText(R.string.dateEN)
            dataBinding.buttonFavorites.setText(R.string.addFavoritesEN)
            dataBinding.buttonProfitAndLoss.setText(R.string.profitAndLossEN)
        }
    }

    private fun observeLiveData(){

        viewModel.addedToFavorites.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){
                    Log.d("bilgi","Eklendi")
                }
                else
                {
                    Log.d("bilgi","Eklenmedi")
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