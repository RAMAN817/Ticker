package com.example.ticker.data.api

import com.example.ticker.data.model.Article
import com.example.ticker.data.model.Stock
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("news")
    suspend fun getNews(
        @Query("category")  category:String = "general"

    ): List<Article>

    @GET("stock")
    suspend fun getStock(
        @Query("symbol") symbol: String
    ): Stock


}
