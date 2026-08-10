package com.example.ticker.data.repository

import com.example.ticker.data.api.NewsApiService
import com.example.ticker.data.model.Article
import javax.inject.Inject


class NewsRepository @Inject constructor(
    private val api: NewsApiService
) {
    private val allowedSources = setOf("CNBC", "Bloomberg")

    suspend fun getNews(category: String): Result<List<Article>> =
        try {
            val articles = api.getNews(category)
            val filtered = articles.filter { article ->
                allowedSources.any { source -> article.source.contains(source, ignoreCase = true) }
            }
            Result.success(filtered)
        }
        catch (e: Exception)
        {
            Result.failure(e)
        }

}