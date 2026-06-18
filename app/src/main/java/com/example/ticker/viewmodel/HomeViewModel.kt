package com.example.ticker.viewmodel

import com.example.ticker.data.repository.NewsRepository
import com.example.ticker.data.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private  val stockRepository: StockRepository,
    private  val newsRepository: NewsRepository)
    {




}