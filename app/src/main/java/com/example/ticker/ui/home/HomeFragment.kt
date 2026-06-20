package com.example.ticker.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ticker.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null //storage box that can be empty or full depending on whether view exist
     private val binding get() = _binding!!//Whenever I use binding, return _binding, but force it to be non-null

}