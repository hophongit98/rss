package com.example.newsapplicationn.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplicationn.R
import com.example.newsapplicationn.databinding.ItemRowBinding
import com.example.newsapplicationn.model.RssArticle

class ArticleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mListArticle = arrayListOf<RssArticle>()
    private var mListener: OnItemClickListener? = null
    private var selectedItem = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsViewHolder(ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return mListArticle.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (itemCount > position && position != RecyclerView.NO_POSITION) {
            val newsHolder = holder as NewsViewHolder
            val news = mListArticle[position]
            newsHolder.build(news)
        }
    }

    inner class NewsViewHolder(private var binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun build(article: RssArticle) {
            binding.title.text = article.title
            binding.pubDate.text = article.pubDate
            binding.rlRoot.setOnClickListener {
                val pos = adapterPosition
                selectedItem = pos
                if (pos != RecyclerView.NO_POSITION) {
                    mListener?.onItemClick(article)
                }
            }
        }
    }

    fun setData(data: List<RssArticle>) {
        val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(data.toCollection(ArrayList()), mListArticle))
        diffResult.dispatchUpdatesTo(this)
        mListArticle.clear()
        mListArticle.addAll(data)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(item: RssArticle)
    }
}

