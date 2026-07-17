package com.example.ticker.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.ticker.data.model.Article
import com.example.ticker.databinding.FragmentArticleDetailsBinding
import com.example.ticker.viewmodel.ArticleDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

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
        val article: Article = args.article
        setupWebView(article.url)
    }

    private fun setupWebView(url: String){

    }


}



