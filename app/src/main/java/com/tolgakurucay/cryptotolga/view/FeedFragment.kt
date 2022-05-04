package com.tolgakurucay.cryptotolga.view


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tolgakurucay.cryptotolga.R
import com.tolgakurucay.cryptotolga.adapter.CoinListAdapter
import com.tolgakurucay.cryptotolga.util.Constants
import com.tolgakurucay.cryptotolga.viewmodel.FeedFragmentModel

import kotlinx.android.synthetic.main.fragment_feed.*


class FeedFragment : Fragment() {

    private lateinit var viewModel:FeedFragmentModel
    private var adapter = CoinListAdapter(arrayListOf())
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
            }
            true
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

    private fun observeLiveData(){
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













}

