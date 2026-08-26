package com.example.ticker.ui.home

import com.example.ticker.data.model.Article
import com.example.ticker.data.model.Stock



sealed interface NewsUiState {
    data object Loading : NewsUiState //loading state
    data class Success(val news: List<Article>): NewsUiState //success state
    data class Error(val message:String): NewsUiState  //failure state
    
}
