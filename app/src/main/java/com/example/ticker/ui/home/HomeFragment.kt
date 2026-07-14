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
import com.example.ticker.adaptor.NewsAdaptor
import com.example.ticker.databinding.FragmentHomeBinding
import com.example.ticker.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import android.content.Intent
import android.net.Uri
import androidx.navigation.fragment.findNavController
import com.example.ticker.R
import com.example.ticker.data.model.Article

@AndroidEntryPoint
class HomeFragment: Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    //FragmentHomeBinding -generates class for fragment_home.xml
    private var _binding: FragmentHomeBinding? = null //Fragment can exist but View can be destroyed,so its prevent memory leaks
     private val binding get() = _binding!!//Whenever I use binding, return _binding, but force it to be non-null

    private lateinit var  newsAdaptor: NewsAdaptor
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
            findNavController().navigate(R.id.action_homeFragment_to_articleDetailFragment)


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

        newsAdaptor = NewsAdaptor(onArticleClicked = { article ->
            val action = HomeFragmentDirections.actionHomeFragmentToArticleDetailFragment(article)
            findNavController().navigate(action)
        }
        )
        binding.newsRecyclerView.apply {          // check the actual ID from fragment_home.xml
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdaptor
        }
    }
    private fun renderNews(state: NewsUiState) {
        when (state) {
            is NewsUiState.Loading -> {
                binding.newsProgressBar.visibility = View.VISIBLE
            }
            is NewsUiState.Success -> {
                binding.newsProgressBar.visibility = View.GONE
                newsAdaptor.submitList(state.news)
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
}