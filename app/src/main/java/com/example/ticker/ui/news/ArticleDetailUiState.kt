package com.example.ticker.ui.news

import com.example.ticker.data.model.ArticleContent


//Success is data class , because only it need to carry data
sealed interface ArticleDetailUiState {
    object Loading : ArticleDetailUiState

    data class Success(val content: ArticleContent): ArticleDetailUiState
    object Error: ArticleDetailUiState

}