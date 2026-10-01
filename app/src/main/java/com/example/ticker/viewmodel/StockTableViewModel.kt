package com.example.ticker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ticker.data.local.StockQuoteEntity
import com.example.ticker.data.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


@HiltViewModel
class StockTableViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {

    // Room's Flow only starts emitting once collected, so kick each one off
    // in init — the repository decides internally whether to hit the network.
    val watchlist: StateFlow<List<StockQuoteEntity>> =
        emptyFlowUntilLoaded { repository.getWatchlist()}


    val  largeCap: StateFlow<List<StockQuoteEntity>> =
        emptyFlowUntilLoaded { repository.getLargeCap()}

    val  indices: StateFlow<List<StockQuoteEntity>> =
        emptyFlowUntilLoaded { repository.getIndices()}


    fun onPullToRefresh(category: String, symbols: List<String>){
        viewModelScope.launch{
            repository.forceRefresh(category,symbols)
        }
    }

    // Small helper: repository fetches are `suspend`, so we launch them once
    // and expose the resulting Flow as a StateFlow scoped to the ViewModel.
    private fun <T> emptyFlowUntilLoaded(
        block:suspend () -> kotlinx.coroutines.flow.Flow<List<T>>

    ):StateFlow<List<T>>{
        val flow = kotlinx.coroutines.flow.MutableStateFlow<List<T>>(emptyList())
        viewModelScope.launch {
            block().collect{ flow.value = it}
        }
        //stateIn takes  normal flow and turns  it in stateflow
        return flow.stateIn(

            scope = viewModelScope,

            //keep collecting upstream flow while  some is observing
            // when nobody is observing wait 5 seconds before stopping
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue =  emptyList()

        )
    }


}