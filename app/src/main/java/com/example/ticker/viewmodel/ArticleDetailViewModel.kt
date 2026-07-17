package com.example.ticker.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.ticker.data.model.Article
import com.example.ticker.ui.news.ArticleDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel()
 {
    val article :Article = savedStateHandle.get<Article>("article") !!
     private val _uiState = MutableStateFlow<ArticleDetailUiState>(ArticleDetailUiState.Loading)
     val uiState : StateFlow<ArticleDetailUiState> = _uiState.asStateFlow()

     fun onPageStarted(){
         _uiState.value = ArticleDetailUiState.Loading
     }
     fun onPageFinished(){
         _uiState.value = ArticleDetailUiState.Loaded
     }
     fun onPAgeError(){
         _uiState.value = ArticleDetailUiState.Error
     }
}