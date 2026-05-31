package com.example.ticker.data.api
import com.example.ticker.data.model.Stock


import retrofit2.http.GET
import retrofit2.http.Query


interface  StockApiService{
    @GET("quote")
    suspend fun getStock(
        @Query("symbol") symbol: String
    ): Stock

}
