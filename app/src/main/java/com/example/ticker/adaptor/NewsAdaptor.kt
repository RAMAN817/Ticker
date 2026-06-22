package com.example.ticker.adaptor

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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NewsAdaptor(
    private val onArticleClicked: (Article) -> Unit
)
    //ArticleDiffCallback() tells if its same article or different

    : ListAdapter<Article, NewsAdaptor.NewsViewHolder>(ArticleDiffCallback()) {

//onCreateViewHolder() is called when RecyclerView needs a new ViewHolder of the given type to represent an item.

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
        //TODO: Change viewType and create a big box for breaking news
    }

    // Fix 1: added { } so bind() lives inside NewsViewHolder where it belongs
    inner class NewsViewHolder(
        private val binding: ItemNewsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            // Fix 2: .txt → .text (×2)
            binding.articleHeadline.text = article.headline
            binding.articleSummary.text = article.summary

            val formatted = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                .format(Date(article.datetime * 1000L))

            // Fix 4: removed stray single quote, use a proper separator
            binding.articleSourceDate.text = "${article.source} · $formatted"

            if (!article.image.isNullOrBlank()) {
                binding.articleImage.visibility = View.VISIBLE
                binding.articleImage.load(article.image) {
                    crossfade(true)
                    error(R.drawable.ic_broken_image)
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
        // Fix 3: areItemsSame → areItemsTheSame
        override fun areItemsTheSame(oldItem: Article, newItem: Article) =
            oldItem.id == newItem.id

        // Fix 3: areContentsSame → areContentsTheSame
        override fun areContentsTheSame(oldItem: Article, newItem: Article) =
            oldItem == newItem
    }
}