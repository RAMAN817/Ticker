package com.example.ticker.data.repository

import com.example.ticker.data.model.ArticleContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.dankito.readability4j.Readability4J
import okhttp3.OkHttpClient
import javax.inject.Inject
import okhttp3.Request
import okio.IOException
import org.jsoup.Jsoup

class ArticleContentRepository @Inject constructor(
    //Reusing okHttp not creating a new instance
private val okHttpClient: OkHttpClient
)
{
    suspend fun extractArticle(url:String): Result<ArticleContent> =
        withContext(Dispatchers.IO){
            try {
                //creates a Http GET  request
                val request = Request.Builder().url(url).build()
                //execute the request and also check if response is successful
                val html = okHttpClient.newCall(request).execute().use {
                    response ->
                     if (!response.isSuccessful) throw IOException("HTTP error ${response.code}")
                    //Read the HTML returned by the server, if body of response is null throw exception
                    response.body?.string() ?: throw IOException("Empty response body ")

                }
                //Parse raw HTML with Jsoup to pull the metadata that readability does not extract
                val doc = Jsoup.parse(html)
                val imageUrl = extractImageUrl(doc)
                // This handles the raw HTML
                val readability = Readability4J(url , html)
                //Then parsed the HTML
                val article = readability.parse()
                //Maps out to the parsed HTML to ArticleContent
                val content = ArticleContent(
                    title = article.title ?: "",
                    textContent = article.textContent?.trim() ?: "",
                    imageUrl = imageUrl


                )
                if (content.textContent.isBlank())
                {
                    Result.failure(IOException("No content found"))
                }
                else{
                    Result.success(content)
                }

            }
            catch(e: Exception){
                Result.failure(e)

        }

    }

    /**
     * Falls back through common metadata tags, then the first sizeable <img>
     * in the body, since not every site sets Open Graph tags.
     */

   private fun extractImageUrl(doc: org.jsoup.nodes.Document): String? {
       val metaCandidates = listOf(
           "meta[property=og:image]",
           "meta[name=twitter:image:src]",
           "meta[itemprop=image]"
       )
        for(selector in metaCandidates)
        {
            val content = doc.selectFirst(selector)?.attr("content")
            if (!content.isNullOrBlank()) {
                // attr("abs:content") would be cleaner but only works if pulled directly;
                // doc was parsed with baseUri = url, so resolve manually if relative
                return doc.selectFirst(selector)?.attr("abs:content")?.takeIf { it.isNotBlank() }
                    ?: content
            }


   }
}
