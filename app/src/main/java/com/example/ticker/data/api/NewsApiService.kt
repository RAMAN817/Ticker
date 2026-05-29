package com.example.ticker.data.api


import com.example.ticker.data.model.Article
import com.squareup.moshi.JsonClass
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

@JsonClass(generateAdapter = true)
interface NewsApiService {
    @GET("news")
    suspend fun getNews(
        @Query("category")  category:String = "general"

    ): Response<Article>



}
