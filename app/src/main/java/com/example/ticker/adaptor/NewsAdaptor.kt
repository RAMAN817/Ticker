package com.example.ticker.adaptor

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.ticker.R
import com.example.ticker.data.model.Article
import com.example.ticker.databinding.ItemNewsBinding
import com.example.ticker.databinding.ItemNewsFeaturedBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NewsAdaptor(
    //onArticleClicked tells which article is tapped in
    //(ParameterTypes) -> ReturnType
    private val onArticleClicked: (Article) -> Unit
)
    //ArticleDiffCallback() tells if its same article or different

    : ListAdapter<Article, RecyclerView.ViewHolder>(ArticleDiffCallback()) {

        companion object{
            private const val VIEW_TYPE_FEATURED = 0
            private const val VIEW_TYPE_REGULAR = 1

        }

    //if position is 0 its FEATURED,anything else REGULAR
    override fun getItemViewType(position: Int): Int {
        return if (position == 0 ) VIEW_TYPE_FEATURED else VIEW_TYPE_REGULAR
    }



//onCreateViewHolder() is called when RecyclerView needs a new ViewHolder of the given type to represent an item.

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            VIEW_TYPE_FEATURED -> {
                val binding = ItemNewsFeaturedBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                FeaturedNewsViewHolder(binding, onArticleClicked)
            }

            else -> {
                val binding = ItemNewsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

                NewsViewHolder(binding,onArticleClicked)
            }
        }
            }

    //fills that box with actual data from  list
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
                is FeaturedNewsViewHolder -> holder.bind(getItem(position))
                is NewsViewHolder -> holder.bind(getItem(position))

        }

    }

    inner class FeaturedNewsViewHolder(
        private val binding: ItemNewsFeaturedBinding,
        private val onArticleClicked: (Article) -> Unit

    ): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(article: Article) {

            binding.articleHeadline.text = article.headline
            binding.articleSummary.text = article.summary

            val formatted = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                .format(Date(article.datetime * 1000L))


            binding.articleSourceDate.text = "${article.source} · $formatted"

            if (!article.image.isNullOrBlank()) {
                binding.articleImage.visibility = View.VISIBLE
                binding.articleImage.load(article.image) {
                    crossfade(true)

                }
            } else {
                binding.articleImage.visibility = View.GONE
            }

            binding.root.setOnClickListener {
                onArticleClicked(article)
            }
        }

    }

    inner class NewsViewHolder(
        private val binding: ItemNewsBinding,
        private val onArticleClicked: (Article) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(article: Article) {

            binding.articleHeadline.text = article.headline
            binding.articleSummary.text = article.summary

            val formatted = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                .format(Date(article.datetime * 1000L))


            binding.articleSourceDate.text = "${article.source} · $formatted"

            if (!article.image.isNullOrBlank()) {
                binding.articleImage.visibility = View.VISIBLE
                binding.articleImage.load(article.image) {
                    crossfade(true)

                }
            } else {
                binding.articleImage.visibility = View.GONE
            }

            binding.root.setOnClickListener {
                onArticleClicked(article)
            }
        }
    }

    class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {

        override fun areItemsTheSame(oldItem: Article, newItem: Article) =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: Article, newItem: Article) =
            oldItem == newItem
    }
}