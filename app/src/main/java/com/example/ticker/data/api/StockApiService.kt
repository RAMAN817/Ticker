package com.example.ticker.data.api
import com.example.ticker.data.model.Stock
import com.squareup.moshi.JsonClass
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

@JsonClass(generateAdapter = true)
interface  StockApiService{
    @GET("stock")
    suspend fun getStock(
        @Query("symbol") symbol: String
    ): Response<Stock>

}
