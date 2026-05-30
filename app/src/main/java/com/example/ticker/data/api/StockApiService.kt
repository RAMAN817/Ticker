package com.example.ticker.data.api
import com.example.ticker.data.model.Stock

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface  StockApiService{
    @GET("stock")
    suspend fun getStock(
        @Query("symbol") symbol: String
    ): Response<Stock>

}
