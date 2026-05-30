package com.example.ticker.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Stock(
    val country: String,
    val currency: String,
    val estimateCurrency: String,
    val exchange: String,
    val ipo:String,
    val logo: String,
    val name: String,
    val marketCapitalization: String,
    val phone: String,
    val ticker: String,
    val weburl: String
)