package com.example.ticker.data.repository

import com.example.ticker.data.model.ArticleContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.dankito.readability4j.Readability4J
import okhttp3.OkHttpClient
import javax.inject.Inject
import okhttp3.Request
import okio.IOException

class ArticleContentRepository @Inject constructor(
    //Reusing okHttp not creating a new instance
private val okHttpClient: OkHttpClient
)
{
    suspend fun extractArticle(url:String): Result<ArticleContent> =
        withContext(Dispatchers.IO){
            try {
                val request = Request.Builder().url(url).build()
                val html = okHttpClient.newCall(request).execute().use {
                    response ->
                     if (!response.isSuccessful) throw IOException("HTTP error ${response.code}")
                    response.body?.string() ?: throw IOException("Empty response body ")

                }
                val readability = Readability4J(url , html)
                val article = readability.parse()
                val content = ArticleContent(
                    title = article.title ?: "",
                    textContent = article.textContent ?: "",
                    imageUrl = article.imageUrl ?: ""


                )
            }
            else{

        }

    }
}
