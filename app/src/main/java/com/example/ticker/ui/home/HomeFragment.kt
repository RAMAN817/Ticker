package com.example.ticker.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        //binding.searchButton because of FragmentHomeBinding else view.findViewBy
        binding.searchButton.setOnClickListener{
            val symbol = binding.searchEditText.text.toString().uppercase()
            viewModel.loadStock(symbol)
        }
        //Starts a coroutine tied to the Fragment’s VIEW lifecycle
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){ //Turn the listener ON when screen is visible, OFF when hidden
                //every time state changes (loading, success, error)
                //react to every new state emitted while listener is ON
                viewModel.uiState.collect { state ->
                    render(state)
                }
            }
        }


    }
}