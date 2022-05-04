package com.tolgakurucay.cryptotolga.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tolgakurucay.cryptotolga.R
import com.tolgakurucay.cryptotolga.databinding.FragmentCoinBinding
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

        arguments?.let {
            id=CoinFragmentArgs.fromBundle(it).coinId

            currency=CoinFragmentArgs.fromBundle(it).currency


        }
        viewModel=ViewModelProviders.of(this@CoinFragment).get(CoinFragmentModel::class.java)
        viewModel.getSingleDataFromAPI(id,currency)



        observeLiveData()


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
                }
                else
                {
                    dataBinding.selectedCryptoLoading.visibility=View.GONE
                }
            }
        })




    }


}