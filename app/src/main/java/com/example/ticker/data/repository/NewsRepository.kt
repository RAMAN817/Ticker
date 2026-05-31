package com.example.ticker.data.repository

import com.example.ticker.data.api.NewsApiService
import com.example.ticker.data.model.Article
import javax.inject.Inject


class NewsRepository @Inject constructor(
    private val api: NewsApiService
) {
    suspend fun getNews(category: String): Result<List<Article>> =
        try {
            Result.success(api.getNews(category))

        }
        catch (e: Exception)
        {
            Result.failure(e)
        }

}