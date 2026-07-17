package com.example.ticker.ui.news

sealed interface ArticleDetailUiState {
    object Loading : ArticleDetailUiState
    object Loaded: ArticleDetailUiState
    object Error: ArticleDetailUiState

}