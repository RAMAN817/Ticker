package com.example.ticker.ui.home

import com.example.ticker.data.model.Article
import com.example.ticker.data.model.Stock

sealed interface StockUiState {
    data object Loading : StockUiState //loading state
    data class Success(val stock: Stock): StockUiState //success state
    data class Error(val message:String): StockUiState  //before we start
}

sealed interface NewsUiState {
    data object Loading : NewsUiState //loading state
    data class Success(val news: Article): NewsUiState //success state
    data class Error(val message:String): NewsUiState  //before we start
    
}
