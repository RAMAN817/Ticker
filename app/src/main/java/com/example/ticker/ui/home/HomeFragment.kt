package com.example.ticker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ticker.databinding.FragmentHomeBinding
import com.example.ticker.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment: Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    //FragmentHomeBinding -generates class for fragment_home.xml
    private var _binding: FragmentHomeBinding? = null //Fragment can exist but View can be destroyed,so its prevent memory leaks
     private val binding get() = _binding!!//Whenever I use binding, return _binding, but force it to be non-null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root //Gives Android the actual UI view hierarchy to display
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        //binding.searchButton because of FragmentHomeBinding else view.findViewBy
        setupNewsRecyclerView()

        binding.searchButton.setOnClickListener{
            val symbol = binding.searchEditText.text.toString().trim().uppercase()
            if (symbol.isNotEmpty())viewModel.loadStock(symbol)
        }
        //Starts a coroutine tied to the Fragment’s VIEW lifecycle
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){ //Turn the listener ON when screen is visible, OFF when hidden
                //every time state changes (loading, success, error)
                //react to every new state emitted while listener is ON
                viewModel.stockState.collect { state ->
                    renderStock(state)
                }
            }
        }
        // Collect news state — auto-loads because ViewModel calls loadNews() in init {}
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) { //Turn the listener ON when screen is visible, OFF when hidden
                //every time state changes (loading, success, error)
                //react to every new state emitted while listener is ON
                viewModel.newsState.collect { state ->
                    renderNews(state)
                }
            }
        }

    }
    private fun setupNewsRecyclerView() {
        //TODO
        //binding.newsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

    }
    private fun renderStock(state: StockUiState){
        when(state) {
            is StockUiState.Idle -> {
                binding.stockProgressBar.visibility = View.GONE
                binding.stockErrorText.visibility = View.GONE
            }
            is StockUiState.Loading -> {
                binding.stockProgressBar.visibility = View.VISIBLE
                binding.stockErrorText.visibility = View.GONE
            }
            is StockUiState.Success -> {
                binding.stockProgressBar.visibility = View.GONE
                binding.stockErrorText.visibility = View.GONE
                // e.g. binding.stockPriceText.text = state.stock.currentPrice.toString()
            }
            is StockUiState.Error -> {
                binding.stockProgressBar.visibility = View.GONE
                binding.stockErrorText.visibility = View.VISIBLE
                binding.stockErrorText.text = state.message
            }
        }
    }
    private fun renderNews(state: NewsUiState) {
        when (state) {
            is NewsUiState.Loading -> {
                binding.newsProgressBar.visibility = View.VISIBLE
            }
            is NewsUiState.Success -> {
                binding.newsProgressBar.visibility = View.GONE
                // TODO: newsAdapter.submitList(state.articles)
            }
            is NewsUiState.Error -> {
                binding.newsProgressBar.visibility = View.GONE
                // optionally show an error banner for news
            }
        }
    }
    // Null the binding — Fragment view can die before the Fragment itself
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

}