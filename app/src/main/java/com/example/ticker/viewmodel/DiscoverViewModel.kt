package com.example.ticker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ticker.data.repository.StockRepository
import com.example.ticker.ui.home.StockUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
   class DiscoverViewModel @Inject constructor(
   private val stockRepository: StockRepository

): ViewModel()
{
   private val _stockState =
      MutableStateFlow<StockUiState>(StockUiState.Idle)//gets the  Idle when started
   val stockState: StateFlow<StockUiState> = _stockState.asStateFlow()

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


}