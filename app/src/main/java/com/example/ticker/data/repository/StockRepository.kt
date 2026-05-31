package com.example.ticker.data.repository

import com.example.ticker.data.api.StockApiService
import com.example.ticker.data.model.Stock
import javax.inject.Inject

class StockRepository @Inject constructor(
    private val api: StockApiService
) {
    suspend fun getStock(symbol: String): Result<Stock> =
        try {
            Result.success(api.getStock(symbol))
        } catch (e: Exception) {
            Result.failure(e)
        }
}