package com.example.ticker.viewmodel

import com.example.ticker.data.model.Stock
import com.example.ticker.data.repository.NewsRepository
import com.example.ticker.data.repository.StockRepository
import com.example.ticker.ui.home.NewsUiState
import com.example.ticker.ui.home.StockUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private  val stockRepository: StockRepository,
    private  val newsRepository: NewsRepository)
    {
private val _stockState = MutableStateFlow<StockUiState>(StockUiState.Idle)
val stockState: StateFlow<StockUiState> = _stockState.asStateFlow()
private val _newsState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
val newsState: StateFlow<NewsUiState> = _newsState.asStateFlow()


//because we want to load news once page is open
init {
    loadNews()
}
        fun loadStock(symbol:String) {





        }
        fun loadNews() {

        }



}