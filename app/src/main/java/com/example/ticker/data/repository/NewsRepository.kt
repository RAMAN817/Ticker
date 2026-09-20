package com.example.ticker.data.repository

import android.util.Log
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
            Log.d("NewsRepo", "Raw articles: ${articles.size}, sources: ${articles.map { it.source }.distinct()}")
            val filtered = articles.filter { article ->
                allowedSources.any { source -> article.source.contains(source, ignoreCase = true) }
            }
            Log.d("NewsRepo", "Filtered articles: ${filtered.size}")
            Result.success(filtered)
        }
        catch (e: Exception)
        {
            Result.failure(e)
        }

}