package com.example.ticker.ui.discover

import com.example.ticker.data.model.Stock



sealed interface DiscoverUiState{
    data object Loading: DiscoverUiState
    data class  Success(
        val data: List<Stock>
    ): DiscoverUiState
    data class Error(val message:String):
        DiscoverUiState  //failure state
}
