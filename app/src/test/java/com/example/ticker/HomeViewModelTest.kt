package com.example.ticker

import com.example.ticker.data.model.Article
import com.example.ticker.data.repository.NewsRepository
import com.example.ticker.viewmodel.HomeViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import app.cash.turbine.*
import com.example.ticker.ui.home.NewsUiState
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Before


class HomeViewModelTest {
  //creating dispatcher for test to tell when and how to run the coroutines
  private val testDispatcher = StandardTestDispatcher()

  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain()
  }

  private val mock = mockk<NewsRepository>()

  @Test
  fun testViewModel() = runTest {
    coEvery {
      mock.getNews(any())
    } returns Result.success(
      listOf(
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
        )
      )
    )
    val viewModel = HomeViewModel(mock)
    // init{} already fires loadNews, so general comes first

    viewModel.newsState.test {
      // awaitItem() -> Wait for the Flow to emit its next value, then give me that value
      assertEquals(NewsUiState.Loading, awaitItem())

      testDispatcher.scheduler.advanceUntilIdle() // let the queued coroutine actually run now

      val initial = awaitItem()
      assertTrue(initial is NewsUiState.Success)

      // Now the call that actually run this function
      viewModel.loadNews("tech")
      testDispatcher.scheduler.advanceUntilIdle() // let THIS coroutine run to completion too

      assertEquals(NewsUiState.Loading, awaitItem())

      val result = awaitItem()
      assertTrue(result is NewsUiState.Success)
      val news = (result as NewsUiState.Success).news
      assertEquals(1, news.size)
      assertEquals("Trump planned xyz", news[0].headline)

      cancelAndIgnoreRemainingEvents()
    }
  }

  @Test
  fun throwError() = runTest {
    coEvery { mock.getNews(any()) } returns Result.failure(Exception("Error"))
    val viewModel = HomeViewModel(mock)
    viewModel.newsState.test {
      assertEquals(NewsUiState.Loading, awaitItem())

      testDispatcher.scheduler.advanceUntilIdle() // let the queued coroutine actually run now

      val result = awaitItem()
      assertTrue(result is NewsUiState.Error)
      assertEquals("Error", (result as NewsUiState.Error).message)
      cancelAndIgnoreRemainingEvents()
    }
  }
}