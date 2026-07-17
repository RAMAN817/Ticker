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
        setupWebView(viewModel.article.url)
        observeUiState()
    }
    private fun observeUiState(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect {
                    state ->
                    binding.progressBar.visibility = when(state){
                        ArticleDetailUiState.Loading ->View.VISIBLE
                        else -> View.GONE
                    }
                }

            }
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView(url: String){
        binding.webView.apply{
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true

            webViewClient = object: WebViewClient(){
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    viewModel.onPageStarted()
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    viewModel.onPageFinished()
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    viewModel.onPageError()
                }

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean = false
            }

            loadUrl(url)


        }

    }


}



