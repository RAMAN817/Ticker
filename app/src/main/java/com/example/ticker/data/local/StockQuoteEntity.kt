package com.example.ticker.data.local

import  androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stock_quotes")

data class StockQuoteEntity(
    @PrimaryKey val symbol : String,
    val companyName : String?,
    val currentPrice : Double,
    val change: Double,
    val percentChange: Double,
    val low: Double,
    val high: Double,
    val open: Double,
    val  previousClose: Double,
    val lastUpdated: Long,
    val category: String // watchlist, largeCap, Index
)

object StockCategory{
    const val WATCHLIST = "watchlist"
    const val LARGE_CAP = "large_cap"
    const val INDEX = "index"
}