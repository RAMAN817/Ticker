import com.example.ticker.data.api.NewsApiService
import com.example.ticker.data.model.Article
import com.example.ticker.data.repository.NewsRepository
import io.mockk.*
import  org.junit.Assert.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test


class NewsRepositoryTest {
     private val mock = mockk<NewsApiService>()
     private val repository = NewsRepository(mock)


    @Test
    fun articleTest() = runTest{

        coEvery {
            mock.getNews(any())
        } returns listOf(
            Article(
                category = "tech",
                datetime = 202689,
                url = "https://www.cnn.com/business",
                headline = "Trump planned xyz",
                id = 1,
                image = null,
                related = "",
                source = "CNBC",
                summary = "summary text"


            ),
            Article(
                category = "tech",
                datetime = 202689,
                url = "https://www.cnn.com/business",
                headline = "Trump planned xyz",
                id = 1,
                image = null,
                related = "",
                source = "Bloomberg",
                summary = "summary text"

            ),
            Article(
                category = "tech",
                datetime = 202689,
                url = "https://www.cnn.com/business",
                headline = "Trump planned xyz",
                id = 1,
                image = null,
                related = "",
                source = "Reuters",
                summary = "summary text"
            )
        )

        val result = repository.getNews("tech")
        coVerify {
            mock.getNews("tech")
        }

        val articles = result.getOrNull()
        assertTrue(result.isSuccess)
        assertEquals(2,articles!!.size )
        assertEquals( "CNBC",articles[0].source)
        assertEquals("Bloomberg",articles[1].source)
        assertTrue(articles.none { it.source == "Reuters" })






    }
    @Test
    fun throwError() = runTest {
        coEvery {
            mock.getNews(any())
        } throws Exception("error")

        val result = repository.getNews("tech")
        coVerify {
            mock.getNews("tech")
        }
        assertTrue(result.isFailure)
        assertEquals("error",result.exceptionOrNull()!!.message)


    }




}
