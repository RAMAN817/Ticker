package com.example.ticker.ui.news
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.ticker.databinding.FragmentArticleDetailsBinding
import com.example.ticker.viewmodel.ArticleDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleDetailFragment: Fragment() {
    private val viewModel : ArticleDetailViewModel by viewModels()
    private val args: ArticleDetailFragmentArgs by navArgs()
    private var _binding : FragmentArticleDetailsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleDetailsBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
        observeUiState()
    }
    private fun observeUiState(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect {
                    state ->
                    binding.progressBar.visibility = View.GONE
                    binding.errorView.visibility = View.GONE
                    binding.scrollView.visibility = View.GONE

                    when(state) {
                        is ArticleDetailUiState.Loading -> {
                             binding.progressBar.visibility = View.VISIBLE

                        }
                        is ArticleDetailUiState.Success ->{
                            binding.scrollView.visibility = View.VISIBLE
                            binding.articleTitle.text = state.content.title
                            binding.articleBody.text = state.content.textContent
                            Glide.with(this@ArticleDetailFragment)
                                .load(state.content.imageUrl)
                                .into(binding.articleImage)

                    }
                        is ArticleDetailUiState.Error -> {
                            binding.errorView.visibility = View.VISIBLE
                            binding.retryButton.setOnClickListener { viewModel.retry()                                                          }
                        }


                }


            }
        }
    }


        }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null


    }


}



