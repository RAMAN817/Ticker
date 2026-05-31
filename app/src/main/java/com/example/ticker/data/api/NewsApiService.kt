package com.example.ticker.data.api


import com.example.ticker.data.model.Article

import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApiService {
    @GET("news")
    suspend fun getNews(
        @Query("category")  category:String = "general"

    ): List<Article>



}
