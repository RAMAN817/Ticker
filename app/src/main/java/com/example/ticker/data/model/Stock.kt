package com.example.ticker.data.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Stock(
    @SerializedName("c")  val current: Double,
    @SerializedName("h")  val high: Double,
    @SerializedName("l")  val low: Double,
    @SerializedName("o")  val open: Double,
    @SerializedName("pc") val previousClose: Double,
    @SerializedName("d")  val change: Double,
    @SerializedName("dp") val changePercent: Double,
    @SerializedName("t")  val timestamp: Long
)