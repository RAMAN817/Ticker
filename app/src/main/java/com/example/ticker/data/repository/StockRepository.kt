package com.example.ticker.data.repository

import android.R
import com.example.ticker.data.api.StockApiService
import com.example.ticker.data.local.StockQuoteDao
import com.example.ticker.data.local.StockQuoteEntity
import com.example.ticker.data.model.Stock
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Singleton
class StockRepository @Inject constructor(
    private val api: StockApiService,
    private val dao: StockQuoteDao
) {
    companion object{
       //Finnhub allows 60 calls per min .So,need some time before making another call

        private const val CACHE_TTL_MS = 90_000L // 90 seconds

        val WATCHLIST_SYMBOLS  = listOf("SPCX","AMD","PLTR","LMT","TSLA")
        val LARGE_CAP_SYMBOLS  = listOf("AAPL","GOOGL","AMZN","NVD","MSFT")

        val INDEX_SYMBOLS = mapOf("SPY" to "S&P 500",
            "DIA" to "Dow Jones Industrial Average")

    }

    //this returns cached data instantly and refresh in background only when
    //cache is empty or stale

    suspend fun observeQuotes(category: String, symbols:List<String>): Flow<List<StockQuoteEntity>> {

        if(isStale(category)){
            refresh(category, symbols)
        }

        return dao.observerByCategory(category )
    }


    //this bypasses TTL check
    suspend fun forceRefresh(category: String, symbols: List<String>) {
        refresh(category, symbols)
    }
    private suspend  fun isStale(category: String): Boolean{
        val oldest = dao.getOldestTimeStamp(category) ?: return  true
        return System.currentTimeMillis() - oldest >  CACHE_TTL_MS

    }

    private suspend fun refresh(category: String, symbols: List<String>){
        val refresh = symbols.mapNotNull {symbol ->
            runCatching {
                val quote =  api.getStock(symbol)
              //GET  /quote?symbol
                StockQuoteEntity(
                    symbol = symbol,
                    companyName = null,
                    currentPrice = quote.current,
                    change = quote.change,
                    percentChange = quote.changePercent,
                    high = quote.high,
                    low =   quote.low,
                    open = quote.open,
                    previousClose = quote.previousClose,
                    lastUpdated = System.currentTimeMillis(),
                    category = category



                )
            }.getOrNull()
        }

    }

}