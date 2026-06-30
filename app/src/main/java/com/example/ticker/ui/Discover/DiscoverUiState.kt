package com.example.ticker.ui.Discover

import com.example.ticker.data.model.Stock

class DiscoverUiState {
    sealed interface StockUiState {
        data object Loading : StockUiState //loading state
        data class Success(val stock: Stock): StockUiState //success state
        data class Error(val message:String): StockUiState  //failure start
        data object Idle : StockUiState
    }
}