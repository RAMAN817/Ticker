package com.example.ticker.ui.home

import com.example.ticker.data.model.Stock

sealed interface HomeUiState {
    data object loading : HomeUiState //loading state
    data class Sucess(val stock: Stock): HomeUiState //success state
    data class Error(val message:String): HomeUiState //error state
    data object Idle: HomeUiState //idle state , before we start
}

