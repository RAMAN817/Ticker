package com.example.ticker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ticker.data.model.Stock
import com.example.ticker.data.repository.NewsRepository
import com.example.ticker.data.repository.StockRepository
import com.example.ticker.ui.home.NewsUiState
import com.example.ticker.ui.home.StockUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val stockRepository: StockRepository,
    private val newsRepository: NewsRepository
) : ViewModel() {
    private val _stockState =
        MutableStateFlow<StockUiState>(StockUiState.Idle)//gets the  Idle when started
    val stockState: StateFlow<StockUiState> = _stockState.asStateFlow()
    private val _newsState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val newsState: StateFlow<NewsUiState> = _newsState.asStateFlow()


    //because we want to load news once page is open
    init {
        loadNews()
    }

    fun loadStock(symbol: String) {
        viewModelScope.launch {
            _stockState.value = StockUiState.Loading
            val result = stockRepository.getStock(symbol)

            result.onSuccess { //if  successfully got  symbol
                    stock ->
                _stockState.value = StockUiState.Success(stock)

            }.onFailure { exception ->  //else
                _stockState.value = StockUiState.Error(
                    exception.message ?: "unknown error"
                )
            }
        }


    }

    fun loadNews(category: String = "general") {
        viewModelScope.launch {

            _newsState.value = NewsUiState.Loading
            val result = newsRepository.getNews(category)
            result.onSuccess { //On success, the lambda hands the value
                    news ->
                _newsState.value = NewsUiState.Success(news)

            }.onFailure { exception ->  //else
                _newsState.value = NewsUiState.Error(
                    exception.message ?: "unknown error"
                )

            }

        }


    }
}