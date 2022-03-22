package com.tolgakurucay.cryptotolga.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tolgakurucay.cryptotolga.model.Coin
import android.view.View
import androidx.databinding.DataBindingUtil
import com.tolgakurucay.cryptotolga.R
import com.tolgakurucay.cryptotolga.databinding.RecyclerViewRowBinding

class CoinListAdapter(val arrayList: ArrayList<Coin>) : RecyclerView.Adapter<CoinListAdapter.CoinListHolder>() {
    class CoinListHolder(val view: RecyclerViewRowBinding) : RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinListHolder {
       val inflater=LayoutInflater.from(parent.context)
        val view=DataBindingUtil.inflate<RecyclerViewRowBinding>(inflater, R.layout.recycler_view_row,parent,false)
        return CoinListHolder(view)

    }

    override fun onBindViewHolder(holder: CoinListHolder, position: Int) {
        holder.view.coin=arrayList[position]

    }

    override fun getItemCount(): Int {
       return arrayList.size
    }
}