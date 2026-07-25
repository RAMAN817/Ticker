package com.example.ticker.data.repository

import com.example.ticker.data.model.ArticleContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import javax.inject.Inject

class ArticleContentRepository @Inject constructor(
    //Reusing okHttp not creating a new instance
private val okHttpClient: OkHttpClient
)
{
    suspend fun extractArticle(url:String): Result<ArticleContent> =
        withContext(Dispatchers.IO){
        try {

        }

    }
}
