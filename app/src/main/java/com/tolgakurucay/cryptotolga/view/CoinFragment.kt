package com.tolgakurucay.cryptotolga.view

import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat.Token.fromBundle
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
import androidx.viewbinding.ViewBinding
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

        viewModel.coin.observe(viewLifecycleOwner, Observer {
            it?.let {


                    dataBinding.coin=it.get(0)


            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it==true){
                    dataBinding.selectedCryptoLoading.visibility=View.VISIBLE
                    dataBinding.coinCodeTextView.visibility=View.GONE
                    dataBinding.coinNameTextView.visibility=View.GONE
                    dataBinding.coinPriceTextView.visibility=View.GONE
                    dataBinding.coinMarketCapTextView.visibility=View.GONE

                }
                else
                {
                    dataBinding.selectedCryptoLoading.visibility=View.GONE
                    dataBinding.coinCodeTextView.visibility=View.VISIBLE
                    dataBinding.coinNameTextView.visibility=View.VISIBLE
                    dataBinding.coinPriceTextView.visibility=View.VISIBLE
                    dataBinding.coinMarketCapTextView.visibility=View.VISIBLE
                }
            }
        })




    }


}