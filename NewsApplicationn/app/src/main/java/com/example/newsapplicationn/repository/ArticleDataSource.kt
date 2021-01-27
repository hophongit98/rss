package com.example.newsapplicationn.repository

import com.example.newsapplicationn.model.RssArticle

interface ArticleDataSource {
    suspend fun fetchNews(url: String): List<RssArticle>
}