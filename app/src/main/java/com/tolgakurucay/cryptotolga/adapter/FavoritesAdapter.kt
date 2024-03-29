package com.tolgakurucay.cryptotolga.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tolgakurucay.cryptotolga.model.Coin
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.tolgakurucay.cryptotolga.R
import com.tolgakurucay.cryptotolga.databinding.RecyclerViewRowBinding
import com.tolgakurucay.cryptotolga.util.Constants
import com.tolgakurucay.cryptotolga.view.FavoritesFragmentDirections
import com.tolgakurucay.cryptotolga.view.FeedFragmentDirections

import kotlinx.android.synthetic.main.recycler_view_row.view.*



class FavoritesAdapter(val arrayList: ArrayList<Coin>,var navController: NavController) :
    RecyclerView.Adapter<FavoritesAdapter.CoinListHolder>(),CoinClickListener {


    class CoinListHolder(val view: RecyclerViewRowBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinListHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<RecyclerViewRowBinding>(
            inflater,
            R.layout.recycler_view_row,
            parent,
            false
        )
        return CoinListHolder(view)

    }

    override fun onBindViewHolder(holder: CoinListHolder, position: Int) {
        holder.view.coin = arrayList[position]
        holder.view.listener=this

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }



    fun updateCoinList(newCoinList: List<Coin>) {
        arrayList.clear()
        arrayList.addAll(newCoinList)
        notifyDataSetChanged()

    }

    override fun onCoinClicked(v: View) {






        val action=FavoritesFragmentDirections.actionFavoritesFragmentToCoinFragment()
        action.setCoinId(v.coinName.text.toString())
        action.setCurrency(Constants.curr)
        navController.navigate(action)



        Log.d("bilgi",v.coinName.text.toString()+"  "+Constants.curr)








    }
}