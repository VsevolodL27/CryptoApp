package com.example.cryptoapp.presentation.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ItemCoinInfoBinding
import com.example.cryptoapp.data.network.model.CoinInfoDto
import com.squareup.picasso.Picasso

class CoinInfoAdapter(private val context: Context) : RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var coinInfoList: List<CoinInfoDto> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onCoinClickListener: OnCoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCoinInfoBinding.inflate(inflater, parent, false)
        return CoinInfoViewHolder(binding)
    }

    override fun getItemCount(): Int = coinInfoList.size

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        with(holder) {
            with(coin) {
                val symbolsTemplate = context.resources.getString(R.string.symbols_template)
                val lastUpdateTemplate = context.resources.getString(R.string.last_update_template)
                tvSymbols.text = String.format(symbolsTemplate, fromSymbol, toSymbol)
                tvPrice.text = price
                tvLastUpdate.text = String.format(lastUpdateTemplate, getFormattedTime())
                Picasso.get().load(getFullImageUrl()).into(ivLogoCoin)
                itemView.setOnClickListener {
                    onCoinClickListener?.onCoinClick(this)
                }
            }
        }

    }

    inner class CoinInfoViewHolder(private val binding: ItemCoinInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val ivLogoCoin: ImageView = binding.ivLogoCoin
        val tvSymbols: TextView = binding.tvSymbols
        val tvPrice: TextView = binding.tvPrice
        val tvLastUpdate: TextView = binding.tvLastUpdate
    }

    interface OnCoinClickListener {
        fun onCoinClick(coinPriceInfo: CoinInfoDto)
    }
}
