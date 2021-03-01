package com.jordan.newsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jordan.newsapp.databinding.ItemArticlePreviewBinding
import com.jordan.newsapp.model.Article

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(var viewBinding: ItemArticlePreviewBinding):
            RecyclerView.ViewHolder(viewBinding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticlePreviewBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.viewBinding.apply {
            Glide.with(itemArticlePreview).load(article.urlToImage).into(ivArticleImage)
            tvDescription.text = article.description
            tvPublishedAt.text = article.publishedAt
            tvSource.text = article.source?.name
            tvTitle.text = article.title
            itemArticlePreview.setOnClickListener {
                onItemClickListener?.let { it(article) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}