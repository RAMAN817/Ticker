package com.example.ticker.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ticker.data.model.Article
import com.example.ticker.data.repository.ArticleContentRepository
import com.example.ticker.ui.news.ArticleDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: ArticleContentRepository
): ViewModel() {
    //gives the StateFlow its initial state when the ViewModel is created
    val article: Article = savedStateHandle.get<Article>("article")!!
    private val _uiState = MutableStateFlow<ArticleDetailUiState>(ArticleDetailUiState.Loading)
    val uiState: StateFlow<ArticleDetailUiState> = _uiState.asStateFlow()

    init {
        loadArticleContent()
    }
    private fun loadArticleContent(){
        viewModelScope.launch {
            //updates the UI every time a new loading operation begins (including retries)
            _uiState.value = ArticleDetailUiState.Loading
            repository.extractArticle(article.url)
                .onSuccess {
                    content ->
                    _uiState.value = ArticleDetailUiState.Success(content)
                }
                .onFailure {
                    content ->
                    _uiState.value = ArticleDetailUiState.Error
                }

        }

    }
    fun retry() = loadArticleContent()
}