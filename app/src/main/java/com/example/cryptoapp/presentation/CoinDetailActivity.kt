package com.example.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.R
import com.example.cryptoapp.data.network.ApiFactory.BASE_IMAGE_URL
import com.example.cryptoapp.utils.convertTimestampToTime
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_coin_detail.ivLogoCoin
import kotlinx.android.synthetic.main.activity_coin_detail.tvFromSymbol
import kotlinx.android.synthetic.main.activity_coin_detail.tvLastMarket
import kotlinx.android.synthetic.main.activity_coin_detail.tvLastUpdate
import kotlinx.android.synthetic.main.activity_coin_detail.tvMaxPrice
import kotlinx.android.synthetic.main.activity_coin_detail.tvMinPrice
import kotlinx.android.synthetic.main.activity_coin_detail.tvPrice
import kotlinx.android.synthetic.main.activity_coin_detail.tvToSymbol

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)
        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL) ?: EMPTY_SYMBOL
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.getDetailInfo(fromSymbol).observe(this) {
            tvPrice.text = it.price
            tvMinPrice.text = it.lowDay
            tvMaxPrice.text = it.highDay
            tvLastMarket.text = it.lastMarket
            tvLastUpdate.text = convertTimestampToTime(it.lastUpdate)
            tvFromSymbol.text = it.fromSymbol
            tvToSymbol.text = it.toSymbol
            Picasso.get().load(BASE_IMAGE_URL + it).into(ivLogoCoin)
        }
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"
        private const val EMPTY_SYMBOL = ""

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}